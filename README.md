[![Android Jitpacks](https://jitpack.io/v/thuannv/HeartsFlying.svg)](https://jitpack.io/#thuannv/HeartsFlying)



# HeartsFlying
Bigo Live like heartlayout.

![hearts_flying.gif](assets/hearts_flying.gif)

This library is an optimization version of source code of HeartsLayout of tyrantgit at [tyrantgit/HeartsLayout](https://github.com/tyrantgit/HeartLayout). The HeartsLayout library of Tyrantgit has done its good job. But, There was a problem about the performance since too many HeartViews add/remove cause UI lagging if we play a stream with thousands of people who are sending hearts at the same time.

This library optimize the case above by custom hearts layout and self-draw hearts. This means no more life cycle methods (measure, layout) run when add/remove when thousands of hearts are added.


## Getting started

In your `build.gradle`:

```gradle
 dependencies {
   compile 'com.github.thuannv:HeartsFlying:v1.0'
 }
```

```java
Heartlayout heartLaoyut = (HeartLayout) findViewById(R.id.HeartsLayout);
...
Drawable drawable = ...;
HeartDrawable heartDrawable = new HeartDrawable(drawable);
HeartLayout.add(heartDrawable);
```

## License

    Copyright (C) 2017 thuannv

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
