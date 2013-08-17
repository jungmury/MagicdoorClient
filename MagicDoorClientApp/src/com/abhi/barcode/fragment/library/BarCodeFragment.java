/*
   Copyright [2013] [Abhinava Srivastava]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package com.abhi.barcode.fragment.library;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import jy.kim.lancs.magicdoor.announcement.Announcement;
import jy.kim.lancs.magicdoor.announcement.ShowAnnouncementActivity;
import jy.kim.lancs.magicdoor.appointment.MakeAppointmentsActivity;
import jy.kim.lancs.magicdoor.bean.AnnouncementRespBean;
import jy.kim.lancs.magicdoor.bean.TimeTableBean;
import jy.kim.lancs.magicdoor.bean.WebServiceInfoRespBean;
import jy.kim.lancs.magicdoor.message.WriteMessageActivity;
import jy.kim.lancs.magicdoor.query.QueryForMagicDoor;
import jy.kim.lancs.magicdoor.scan.TagModel;
import jy.kim.lancs.magicdoor.timetable.ShowTimeTableActivity;
import jy.kim.lancs.magicdoor.timetable.TimeTable;
import jy.kim.lancs.magicdoor.util.MagicDoorContants;
import magicdoor.lancs.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import com.abhi.barcode.fragment.barcode.BarCodeHandler;
import com.abhi.barcode.fragment.barcode.ViewfinderView;
import com.abhi.barcode.fragment.dialogs.IDialogCreator;
import com.abhi.barcode.fragment.dialogs.MessageDialogs;
import com.abhi.barcode.fragment.interfaces.IConstants;
import com.abhi.barcode.fragment.interfaces.IResultCallback;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.camera.CameraManager;

public class BarCodeFragment extends Fragment implements
		SurfaceHolder.Callback, IConstants, IDialogCreator {

	private static final String TAG = BarCodeFragment.class.getSimpleName();

	private CameraManager cameraManager;
	private BarCodeHandler handler;
	private Result savedResultToShow;
	private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private Collection<BarcodeFormat> decodeFormats;
	private String characterSet;
	private boolean runCamera = false;
	private IResultCallback mCallBack;
	private boolean cameraActive = false;;

	private String userName;

	public boolean isCameraActive() {
		return cameraActive;
	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	private Handler mHandler;

	public void startCameraCampure() {
		cameraManager = new CameraManager(getActivity().getApplicationContext());
		viewfinderView.setCameraManager(cameraManager);
		handler = null;
		resetStatusView();
		runCamera = true;
		SurfaceView surfaceView = (SurfaceView) getView().findViewById(
				R.id.cameraView);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		hasSurface = true;
		if (hasSurface) {
			initCamera(surfaceHolder, viewfinderView);
		} else {
			surfaceHolder.addCallback(BarCodeFragment.this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;
		cameraActive = true;
	}

	public void stopCameraCapture() {
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		cameraManager.closeDriver();
		if (!hasSurface) {
			SurfaceView surfaceView = (SurfaceView) getView().findViewById(
					R.id.cameraView);
			SurfaceHolder surfaceHolder = surfaceView.getHolder();
			surfaceHolder.removeCallback(BarCodeFragment.this);
		}
		cameraActive = false;
	}

	public CameraManager getCameraManager() {
		return cameraManager;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View c = View.inflate(getActivity(), R.layout.superimposedcamera, null);
		viewfinderView = (ViewfinderView) c.findViewById(R.id.viewFinder_View);
		userName = getActivity().getIntent().getStringExtra("userName");
		hasSurface = false;
		mHandler = new Handler();
		runCamera = true;
		return c;
	}

	@Override
	public void onResume() {
		super.onResume();
		if (runCamera && hasSurface) {
			startCameraCampure();
		} else if (runCamera) {
			SurfaceView surfaceView = (SurfaceView) getView().findViewById(
					R.id.cameraView);
			SurfaceHolder surfaceHolder = surfaceView.getHolder();
			surfaceHolder.addCallback(BarCodeFragment.this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
	}

	@Override
	public void onPause() {
		if (runCamera) {
			stopCameraCapture();
		}
		super.onPause();
	}

	private void decodeOrStoreSavedBitmap(Bitmap bitmap, Result result) {
		// Bitmap isn't used yet -- will be used soon
		if (handler == null) {
			savedResultToShow = result;
		} else {
			if (result != null) {
				savedResultToShow = result;
			}
			if (savedResultToShow != null) {
				Message message = Message.obtain(handler, DECODE_COMPLETE,
						savedResultToShow);
				handler.sendMessage(message);
			}
			savedResultToShow = null;
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (holder == null) {
			Log.e(TAG,
					"*** WARNING *** surfaceCreated() gave us a null surface!");
		}
		if (!hasSurface) {
			hasSurface = true;
			if (runCamera)
				startCameraCampure();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	/**
	 * A valid barcode has been found, so give an indication of success and show
	 * the results.
	 * 
	 * @param rawResult
	 *            The contents of the barcode.
	 * @param barcode
	 *            A greyscale bitmap of the camera data which was decoded.
	 */
	public void handleDecode(Result rawResult, Bitmap barcode) {
		drawResultPoints(barcode, rawResult);
		Log.e(TAG, "Value recived: " + rawResult.getText());
		lastResult = rawResult;
		if (mCallBack != null) {
			mCallBack.result(lastResult);
		} else {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					MessageDialogs dialogs = new MessageDialogs(
							BarCodeFragment.this, 0);
					dialogs.show(getFragmentManager(), TAG);
					stopCameraCapture();
				}
			});
		}
		// Toast.makeText(getActivity(), rawResult.getText(),
		// Toast.LENGTH_SHORT).show();

	}

	private Result lastResult;

	/**
	 * Superimpose a line for 1D or dots for 2D to highlight the key features of
	 * the barcode.
	 * 
	 * @param barcode
	 *            A bitmap of the captured image.
	 * @param rawResult
	 *            The decoded results which contains the points to draw.
	 */
	private void drawResultPoints(Bitmap barcode, Result rawResult) {
		ResultPoint[] points = rawResult.getResultPoints();
		if (points != null && points.length > 0) {
			Canvas canvas = new Canvas(barcode);
			Paint paint = new Paint();
			paint.setColor(getResources().getColor(R.color.result_image_border));
			paint.setStrokeWidth(3.0f);
			paint.setStyle(Paint.Style.STROKE);
			Rect border = new Rect(2, 2, barcode.getWidth() - 2,
					barcode.getHeight() - 2);
			canvas.drawRect(border, paint);

			paint.setColor(getResources().getColor(R.color.result_points));
			if (points.length == 2) {
				paint.setStrokeWidth(4.0f);
				drawLine(canvas, paint, points[0], points[1]);
			} else if (points.length == 4
					&& (rawResult.getBarcodeFormat() == BarcodeFormat.UPC_A || rawResult
							.getBarcodeFormat() == BarcodeFormat.EAN_13)) {
				drawLine(canvas, paint, points[0], points[1]);
				drawLine(canvas, paint, points[2], points[3]);
			} else {
				paint.setStrokeWidth(10.0f);
				for (ResultPoint point : points) {
					canvas.drawPoint(point.getX(), point.getY(), paint);
				}
			}
		}
	}

	private static void drawLine(Canvas canvas, Paint paint, ResultPoint a,
			ResultPoint b) {
		canvas.drawLine(a.getX(), a.getY(), b.getX(), b.getY(), paint);
	}

	private void initCamera(SurfaceHolder surfaceHolder, View v) {
		try {
			cameraManager.openDriver(surfaceHolder, v);
			if (handler == null) {
				handler = new BarCodeHandler(this, decodeFormats, characterSet,
						cameraManager);
			}
			decodeOrStoreSavedBitmap(null, null);
		} catch (IOException ioe) {
			Log.w(TAG, ioe);
		} catch (RuntimeException e) {
			Log.w(TAG, "Unexpected error initializing camera", e);
		}
	}

	public void restartPreviewAfterDelay(long delayMS) {
		if (handler != null) {
			handler.sendEmptyMessageDelayed(RESTART_PREVIEW, delayMS);
		}
		resetStatusView();
	}

	private void resetStatusView() {
		viewfinderView.setVisibility(View.VISIBLE);
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();
	}

	@Override
	public Dialog createDialog(int mWhat) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Service Type");
		final String[] result = lastResult.getText().split("&");
		final String serviceDesc = result[1];
		final String tagOwner = result[0];
		TagModel tagModel = new TagModel(getActivity());
		final WebServiceInfoRespBean info = tagModel.getTagOwnerInfo(
				MagicDoorContants.REST_SERVICE_URI_FOR_WEB_SERIVCE_FOR_QR_CODE,
				tagOwner);
		final String tagOwnerName = info.tagOwnerName;
		Log.i("MagicDoor",
				MagicDoorContants.REST_SERVICE_URI_FOR_WEB_SERIVCE_FOR_QR_CODE
						+ tagOwner);
		builder.setMessage(result[1].toUpperCase(Locale.UK));
		builder.setNegativeButton("Proceed",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (result != null) {
							Intent nextActivity = null;
							QueryForMagicDoor query;
							if (serviceDesc.equals("announcement")) {
								// if service description is announcement then
								// forward to announcement activity
								Announcement annModel = new Announcement(
										getActivity());
								query = new QueryForMagicDoor();
								query.uri = MagicDoorContants.REST_SERVICE_URI_FOR_SHOWING_ANNOUNCEMENTS;
								query.tagOwner = tagOwner;
								ArrayList<AnnouncementRespBean> announcements = annModel
										.getAllAnnouncementsOfTheLecturer(query);
								nextActivity = new Intent(getActivity(),
										ShowAnnouncementActivity.class)
										.putExtra("tagOwner", tagOwner)
										.putExtra("announcements",
												announcements);

							} else if (serviceDesc.equals("appointment")) {
								nextActivity = new Intent(getActivity(),
										MakeAppointmentsActivity.class)
										.putExtra("tagOwner", tagOwner)
										.putExtra("tagOwnerName", tagOwnerName)
										.putExtra("userName", userName);
							} else if (serviceDesc.equals("message")) {
								nextActivity = new Intent(getActivity(),
										WriteMessageActivity.class)
										.putExtra("tagOwner", tagOwner)
										.putExtra("tagOwnerName", tagOwnerName)
										.putExtra("userName", userName);
							} else if (serviceDesc.equals("timetable")) {
								nextActivity = new Intent(getActivity(),
										ShowTimeTableActivity.class);
								query = new QueryForMagicDoor();
								query.uri = MagicDoorContants.REST_SERVICE_URI_FOR_GETTING_WEEK_TIME_TABLE;
								query.tagOwner = tagOwner;
								TimeTable timeTableModel = new TimeTable(
										getActivity());
								ArrayList<TimeTableBean> timeTable = timeTableModel
										.getWeekTimeTable(query);
								putExtrasTo(nextActivity, timeTable, userName);
							}
							startActivity(nextActivity);

						}
					}
				}).setPositiveButton("Scan Again",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						startCameraCampure();
					}
				});
		return builder.create();
	}

	public IResultCallback getmCallBack() {
		return mCallBack;
	}

	public void setmCallBack(IResultCallback mCallBack) {
		this.mCallBack = mCallBack;
	}

	private void putExtrasTo(Intent nextActivity,
			ArrayList<TimeTableBean> timeTable, String userName) {
		nextActivity.putExtra("timeTable", timeTable);
		nextActivity.putExtra("userName", userName);
	}
}