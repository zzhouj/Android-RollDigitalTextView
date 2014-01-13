Android-RollDigitalTextView
===========================

Android roll digital text view, digital will rolling from 0 to actual value (increase), or vice versa (decrease).

Features
========

+ Android SDK **level 4+**.
+ Digital will rolling from 0 to actual value (increase), or vice versa (decrease).
+ Options settings: digital type (integer|currency), rolling duration (ms).

Snapshots
=========

[snapshot]: https://github.com/zzhouj/Android-RollDigitalTextView/raw/master/snapshot/snapshot.png  "Snapshot"

![Snapshot][snapshot]

Usage
=====
1. Add layout xml fragment like below:

		<com.coco.rolldigitaltextview.RollDigitalTextView
		    android:id="@+id/roll_digital_text"
		    android:layout_width="match_parent"
		    android:layout_height="0dp"
		    android:layout_weight="1"
		    android:background="@color/orange"
		    android:gravity="center"
		    android:text="RollDigitalTextView"
		    android:textColor="@color/white"
		    android:textSize="35sp" />

2. Options settings:

		// digital type (integer|currency)
		mRollDigitalTextView.setDigitalType(digitalType);

		// rolling duration
		mRollDigitalTextView.setRollingDuration(progress);

		// start rolling increase
		mRollDigitalTextView.startRolling(true);

		// start rolling decrease
		mRollDigitalTextView.startRolling(false);

License
=======

    The MIT License (MIT)
    
    Copyright (c) 2014 justin
    
    Permission is hereby granted, free of charge, to any person obtaining a copy of
    this software and associated documentation files (the "Software"), to deal in
    the Software without restriction, including without limitation the rights to
    use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
    the Software, and to permit persons to whom the Software is furnished to do so,
    subject to the following conditions:
    
    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.
    
    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
    FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
    COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
    IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
    CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
