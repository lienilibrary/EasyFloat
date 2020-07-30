# Download

```
implementation 'com.github.lienilibrary:EasyFloat:1.0.1'
```

# Usage

### step 1: initialize in application

```
EasyFloat.init(this);
```

### step 2: create layout

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
EasyFloat.setView(getWindow(),R.layout.view_float_video,0,0);
```