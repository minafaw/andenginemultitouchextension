package org.anddev.andengine.extenion.input.touch.controller;

import org.anddev.andengine.input.touch.controller.BaseTouchController;

import android.view.MotionEvent;

/**
 * @author Nicolas Gramlich
 * @since 20:23:33 - 13.07.2010
 */
public class MultiTouchController extends BaseTouchController {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================
	
	public MultiTouchController() throws MultiTouchException {
		if(MultiTouch.isSupportedByAndroidVersion() == false) {
			throw new MultiTouchException();
		}
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public boolean onHandleMotionEvent(final MotionEvent pMotionEvent, final ITouchEventCallback pTouchEventCallback) {
		final int action = pMotionEvent.getAction() & MotionEvent.ACTION_MASK;
		switch(action) {
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_POINTER_DOWN:
				return this.onHandleTouchDown(pMotionEvent, pTouchEventCallback);
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_POINTER_UP:
				return this.onHandleTouchUp(pMotionEvent, pTouchEventCallback);
			case MotionEvent.ACTION_MOVE:
				return this.onHandleTouchMove(pMotionEvent, pTouchEventCallback);
			default:
				throw new IllegalArgumentException("Invalid Action detected: " + action);
		}
	}

	// ===========================================================
	// Methods
	// ===========================================================

	private boolean onHandleTouchMove(final MotionEvent pMotionEvent, final ITouchEventCallback pTouchEventCallback) {
		boolean handled = false;
		for(int i = pMotionEvent.getPointerCount() - 1; i >= 0; i--) {
			final int pointerIndex = i;
			final int pointerID = pMotionEvent.getPointerId(pointerIndex);
			final boolean handledInner = BaseTouchController.fireTouchEvent(pMotionEvent.getX(pointerIndex), pMotionEvent.getY(pointerIndex), MotionEvent.ACTION_MOVE, pointerID, pMotionEvent, pTouchEventCallback);
			handled = handled || handledInner;
		}
		return handled;
	}

	private boolean onHandleTouchDown(final MotionEvent pMotionEvent, final ITouchEventCallback pTouchEventCallback) {
		final int pointerDownIndex = this.getPointerIndex(pMotionEvent);
		final int pointerDownID = pMotionEvent.getPointerId(pointerDownIndex);
		return BaseTouchController.fireTouchEvent(pMotionEvent.getX(pointerDownIndex), pMotionEvent.getY(pointerDownIndex), MotionEvent.ACTION_DOWN, pointerDownID, pMotionEvent, pTouchEventCallback);
	}

	private boolean onHandleTouchUp(final MotionEvent pMotionEvent, final ITouchEventCallback pTouchEventCallback) {
		final int pointerUpIndex = this.getPointerIndex(pMotionEvent);
		final int pointerUpID = pMotionEvent.getPointerId(pointerUpIndex);
		return BaseTouchController.fireTouchEvent(pMotionEvent.getX(pointerUpIndex), pMotionEvent.getY(pointerUpIndex), MotionEvent.ACTION_UP, pointerUpID, pMotionEvent, pTouchEventCallback);
	}

	private int getPointerIndex(final MotionEvent pMotionEvent) {
		return (pMotionEvent.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
