# Download

```
implementation 'com.github.lienilibrary:EasyFloat:1.0.2'
```



# Usage

### step 1: initialize in application

```
EasyFloat.init(this);
```

### step 2: create a layout

```
<?xml version="1.0" encoding="utf-8"?>
<com.lieni.library.easyfloat.view.FloatView 
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content">
    <TextView
        android:background="@color/colorAccent"
        android:id="@+id/txPlayer"
        android:layout_width="200dp"
        android:layout_height="200dp"/>
</com.lieni.library.easyfloat.view.FloatView>
```

### step 3: use in activity

```
EasyFloat.setView(getWindow(),R.layout.view_float_video);
```



# license

MIT License

Copyright (c) 2020 lienilibrary

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
