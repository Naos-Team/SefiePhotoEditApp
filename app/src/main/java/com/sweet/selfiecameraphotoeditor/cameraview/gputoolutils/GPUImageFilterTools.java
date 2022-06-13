package com.sweet.selfiecameraphotoeditor.cameraview.gputoolutils;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.opengl.Matrix;
import android.view.View;

import com.sweet.selfiecameraphotoeditor.R;
import com.sweet.selfiecameraphotoeditor.activities.SweetCameraActivity;

import jp.co.cyberagent.android.gpuimage.GPUImage3x3TextureSamplingFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBilateralFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBulgeDistortionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorBalanceFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageContrastFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageCrosshatchFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDissolveBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageEmbossFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageExposureFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGammaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGaussianBlurFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGlassSphereFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHazeFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHighlightShadowFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHueFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLevelsFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageMonochromeFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageOpacityFilter;
import jp.co.cyberagent.android.gpuimage.GPUImagePixelationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImagePosterizeFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageRGBFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSaturationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSepiaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSharpenFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSobelEdgeDetection;
import jp.co.cyberagent.android.gpuimage.GPUImageSphereRefractionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSwirlFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageTransformFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageTwoInputFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageVignetteFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageWhiteBalanceFilter;

public class GPUImageFilterTools {

    public static class FilterAdjuster {
        private final Adjuster<? extends GPUImageFilter> adjuster;

        private abstract class Adjuster<T extends GPUImageFilter> {
            private GPUImageFilter filter;

            public abstract void adjust(int i);

            public float range(int i, float f, float f2) {
                return (((f2 - f) * ((float) i)) / 100.0f) + f;
            }

            private Adjuster() {
            }

            public Adjuster<T> filter(GPUImageFilter gPUImageFilter) {
                this.filter = gPUImageFilter;
                return this;
            }

            public GPUImageFilter getFilter() {
                return this.filter;
            }

            public int range(int i, int i2, int i3) {
                return (((i3 - i2) * i) / 100) + i2;
            }
        }

        private class BilateralAdjuster extends Adjuster<GPUImageBilateralFilter> {
            private BilateralAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageBilateralFilter) getFilter()).setDistanceNormalizationFactor(range(i, 0.0f, 15.0f));
            }
        }

        private class BrightnessAdjuster extends Adjuster<GPUImageBrightnessFilter> {
            private BrightnessAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageBrightnessFilter) getFilter()).setBrightness(range(i, -1.0f, 1.0f));
            }
        }

        private class BulgeDistortionAdjuster extends Adjuster<GPUImageBulgeDistortionFilter> {
            private BulgeDistortionAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageBulgeDistortionFilter) getFilter()).setRadius(range(i, 0.0f, 1.0f));
                ((GPUImageBulgeDistortionFilter) getFilter()).setScale(range(i, -1.0f, 1.0f));
            }
        }

        private class ColorBalanceAdjuster extends Adjuster<GPUImageColorBalanceFilter> {
            private ColorBalanceAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageColorBalanceFilter) getFilter()).setMidtones(new float[]{range(i, 0.0f, 1.0f), range(i / 2, 0.0f, 1.0f), range(i / 3, 0.0f, 1.0f)});
            }
        }

        private class ContrastAdjuster extends Adjuster<GPUImageContrastFilter> {
            private ContrastAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageContrastFilter) getFilter()).setContrast(range(i, 0.0f, 2.0f));
            }
        }

        private class CrosshatchBlurAdjuster extends Adjuster<GPUImageCrosshatchFilter> {
            private CrosshatchBlurAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageCrosshatchFilter) getFilter()).setCrossHatchSpacing(range(i, 0.0f, 0.06f));
                ((GPUImageCrosshatchFilter) getFilter()).setLineWidth(range(i, 0.0f, 0.006f));
            }
        }

        private class DissolveBlendAdjuster extends Adjuster<GPUImageDissolveBlendFilter> {
            private DissolveBlendAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageDissolveBlendFilter) getFilter()).setMix(range(i, 0.0f, 1.0f));
            }
        }

        private class EmbossAdjuster extends Adjuster<GPUImageEmbossFilter> {
            private EmbossAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageEmbossFilter) getFilter()).setIntensity(range(i, 0.0f, 4.0f));
            }
        }

        private class ExposureAdjuster extends Adjuster<GPUImageExposureFilter> {
            private ExposureAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageExposureFilter) getFilter()).setExposure(range(i, -10.0f, 10.0f));
            }
        }

        private class GPU3x3TextureAdjuster extends Adjuster<GPUImage3x3TextureSamplingFilter> {
            private GPU3x3TextureAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImage3x3TextureSamplingFilter) getFilter()).setLineSize(range(i, 0.0f, 5.0f));
            }
        }

        private class GammaAdjuster extends Adjuster<GPUImageGammaFilter> {
            private GammaAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageGammaFilter) getFilter()).setGamma(range(i, 0.0f, 3.0f));
            }
        }

        private class GaussianBlurAdjuster extends Adjuster<GPUImageGaussianBlurFilter> {
            private GaussianBlurAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageGaussianBlurFilter) getFilter()).setBlurSize(range(i, 0.0f, 1.0f));
            }
        }

        private class GlassSphereAdjuster extends Adjuster<GPUImageGlassSphereFilter> {
            private GlassSphereAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageGlassSphereFilter) getFilter()).setRadius(range(i, 0.0f, 1.0f));
            }
        }

        private class HazeAdjuster extends Adjuster<GPUImageHazeFilter> {
            private HazeAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageHazeFilter) getFilter()).setDistance(range(i, -0.3f, 0.3f));
                ((GPUImageHazeFilter) getFilter()).setSlope(range(i, -0.3f, 0.3f));
            }
        }

        private class HighlightShadowAdjuster extends Adjuster<GPUImageHighlightShadowFilter> {
            private HighlightShadowAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageHighlightShadowFilter) getFilter()).setShadows(range(i, 0.0f, 1.0f));
                ((GPUImageHighlightShadowFilter) getFilter()).setHighlights(range(i, 0.0f, 1.0f));
            }
        }

        private class HueAdjuster extends Adjuster<GPUImageHueFilter> {
            private HueAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageHueFilter) getFilter()).setHue(range(i, 0.0f, 360.0f));
            }
        }

        private class LevelsMinMidAdjuster extends Adjuster<GPUImageLevelsFilter> {
            private LevelsMinMidAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageLevelsFilter) getFilter()).setMin(0.0f, range(i, 0.0f, 1.0f), 1.0f);
            }
        }

        private class MonochromeAdjuster extends Adjuster<GPUImageMonochromeFilter> {
            private MonochromeAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageMonochromeFilter) getFilter()).setIntensity(range(i, 0.0f, 1.0f));
            }
        }

        private class OpacityAdjuster extends Adjuster<GPUImageOpacityFilter> {
            private OpacityAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageOpacityFilter) getFilter()).setOpacity(range(i, 0.0f, 1.0f));
            }
        }

        private class PixelationAdjuster extends Adjuster<GPUImagePixelationFilter> {
            private PixelationAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImagePixelationFilter) getFilter()).setPixel(range(i, 1.0f, 100.0f));
            }
        }

        private class PosterizeAdjuster extends Adjuster<GPUImagePosterizeFilter> {
            private PosterizeAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImagePosterizeFilter) getFilter()).setColorLevels(range(i, 1, 50));
            }
        }

        private class RGBAdjuster extends Adjuster<GPUImageRGBFilter> {
            private RGBAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageRGBFilter) getFilter()).setRed(range(i, 0.0f, 1.0f));
            }
        }

        private class RotateAdjuster extends Adjuster<GPUImageTransformFilter> {
            private RotateAdjuster() {
                super();
            }

            public void adjust(int i) {
                float[] fArr = new float[16];
                Matrix.setRotateM(fArr, 0, (float) ((i * 360) / 100), 0.0f, 0.0f, 1.0f);
                ((GPUImageTransformFilter) getFilter()).setTransform3D(fArr);
            }
        }

        private class SaturationAdjuster extends Adjuster<GPUImageSaturationFilter> {
            private SaturationAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageSaturationFilter) getFilter()).setSaturation(range(i, 0.0f, 2.0f));
            }
        }

        private class SepiaAdjuster extends Adjuster<GPUImageSepiaFilter> {
            private SepiaAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageSepiaFilter) getFilter()).setIntensity(range(i, 0.0f, 2.0f));
            }
        }

        private class SharpnessAdjuster extends Adjuster<GPUImageSharpenFilter> {
            private SharpnessAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageSharpenFilter) getFilter()).setSharpness(range(i, -4.0f, 4.0f));
            }
        }

        private class SobelAdjuster extends Adjuster<GPUImageSobelEdgeDetection> {
            private SobelAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageSobelEdgeDetection) getFilter()).setLineSize(range(i, 0.0f, 5.0f));
            }
        }

        private class SphereRefractionAdjuster extends Adjuster<GPUImageSphereRefractionFilter> {
            private SphereRefractionAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageSphereRefractionFilter) getFilter()).setRadius(range(i, 0.0f, 1.0f));
            }
        }

        private class SwirlAdjuster extends Adjuster<GPUImageSwirlFilter> {
            private SwirlAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageSwirlFilter) getFilter()).setAngle(range(i, 0.0f, 2.0f));
            }
        }

        private class VignetteAdjuster extends Adjuster<GPUImageVignetteFilter> {
            private VignetteAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageVignetteFilter) getFilter()).setVignetteStart(range(i, 0.0f, 1.0f));
            }
        }

        private class WhiteBalanceAdjuster extends Adjuster<GPUImageWhiteBalanceFilter> {
            private WhiteBalanceAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageWhiteBalanceFilter) getFilter()).setTemperature(range(i, 2000.0f, 8000.0f));
            }
        }

        public FilterAdjuster(GPUImageFilter gPUImageFilter) {
            if (gPUImageFilter instanceof GPUImageSharpenFilter) {
                this.adjuster = new SharpnessAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageSepiaFilter) {
                this.adjuster = new SepiaAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageContrastFilter) {
                this.adjuster = new ContrastAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageGammaFilter) {
                this.adjuster = new GammaAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageBrightnessFilter) {
                this.adjuster = new BrightnessAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageSobelEdgeDetection) {
                this.adjuster = new SobelAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageEmbossFilter) {
                this.adjuster = new EmbossAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImage3x3TextureSamplingFilter) {
                this.adjuster = new GPU3x3TextureAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageHueFilter) {
                this.adjuster = new HueAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImagePosterizeFilter) {
                this.adjuster = new PosterizeAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImagePixelationFilter) {
                this.adjuster = new PixelationAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageSaturationFilter) {
                this.adjuster = new SaturationAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageExposureFilter) {
                this.adjuster = new ExposureAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageHighlightShadowFilter) {
                this.adjuster = new HighlightShadowAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageMonochromeFilter) {
                this.adjuster = new MonochromeAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageOpacityFilter) {
                this.adjuster = new OpacityAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageRGBFilter) {
                this.adjuster = new RGBAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageWhiteBalanceFilter) {
                this.adjuster = new WhiteBalanceAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageVignetteFilter) {
                this.adjuster = new VignetteAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageDissolveBlendFilter) {
                this.adjuster = new DissolveBlendAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageGaussianBlurFilter) {
                this.adjuster = new GaussianBlurAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageCrosshatchFilter) {
                this.adjuster = new CrosshatchBlurAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageBulgeDistortionFilter) {
                this.adjuster = new BulgeDistortionAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageGlassSphereFilter) {
                this.adjuster = new GlassSphereAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageHazeFilter) {
                this.adjuster = new HazeAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageSphereRefractionFilter) {
                this.adjuster = new SphereRefractionAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageSwirlFilter) {
                this.adjuster = new SwirlAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageColorBalanceFilter) {
                this.adjuster = new ColorBalanceAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageLevelsFilter) {
                this.adjuster = new LevelsMinMidAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageBilateralFilter) {
                this.adjuster = new BilateralAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageTransformFilter) {
                this.adjuster = new RotateAdjuster().filter(gPUImageFilter);
            } else {
                this.adjuster = null;
            }
        }
    }

    public static GPUImageFilter createBlenderFilter(Context context, Class<? extends GPUImageTwoInputFilter> cls) {
        try {
            if (SweetCameraActivity.imgId == 0) {
                GPUImageTwoInputFilter gPUImageTwoInputFilter = (GPUImageTwoInputFilter) cls.newInstance();
                gPUImageTwoInputFilter.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.img_noanyfilter));
                return gPUImageTwoInputFilter;
            } else if (SweetCameraActivity.imgId == 1) {
                GPUImageTwoInputFilter gPUImageTwoInputFilter2 = (GPUImageTwoInputFilter) cls.newInstance();
                gPUImageTwoInputFilter2.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.img_blender_01));
                return gPUImageTwoInputFilter2;
            } else if (SweetCameraActivity.imgId == 2) {
                GPUImageTwoInputFilter gPUImageTwoInputFilter3 = (GPUImageTwoInputFilter) cls.newInstance();
                gPUImageTwoInputFilter3.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.img_blender_02));
                return gPUImageTwoInputFilter3;
            } else if (SweetCameraActivity.imgId == 3) {
                GPUImageTwoInputFilter gPUImageTwoInputFilter4 = (GPUImageTwoInputFilter) cls.newInstance();
                gPUImageTwoInputFilter4.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.img_blender_03));
                return gPUImageTwoInputFilter4;
            } else if (SweetCameraActivity.imgId == 4) {
                GPUImageTwoInputFilter gPUImageTwoInputFilter5 = (GPUImageTwoInputFilter) cls.newInstance();
                gPUImageTwoInputFilter5.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.img_blender_04));
                return gPUImageTwoInputFilter5;
            } else if (SweetCameraActivity.imgId == 5) {
                GPUImageTwoInputFilter gPUImageTwoInputFilter6 = (GPUImageTwoInputFilter) cls.newInstance();
                gPUImageTwoInputFilter6.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.img_blender_05));
                return gPUImageTwoInputFilter6;
            } else if (SweetCameraActivity.imgId == 6) {
                GPUImageTwoInputFilter gPUImageTwoInputFilter7 = (GPUImageTwoInputFilter) cls.newInstance();
                gPUImageTwoInputFilter7.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.img_blender_06));
                return gPUImageTwoInputFilter7;
            } else if (SweetCameraActivity.imgId == 7) {
                GPUImageTwoInputFilter gPUImageTwoInputFilter8 = (GPUImageTwoInputFilter) cls.newInstance();
                gPUImageTwoInputFilter8.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.img_blender_07));
                return gPUImageTwoInputFilter8;
            } else if (SweetCameraActivity.imgId == View.GONE) {
                GPUImageTwoInputFilter gPUImageTwoInputFilter9 = (GPUImageTwoInputFilter) cls.newInstance();
                gPUImageTwoInputFilter9.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.img_blender_08));
                return gPUImageTwoInputFilter9;
            } else if (SweetCameraActivity.imgId == 9) {
                GPUImageTwoInputFilter gPUImageTwoInputFilter10 = (GPUImageTwoInputFilter) cls.newInstance();
                gPUImageTwoInputFilter10.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.img_blender_09));
                return gPUImageTwoInputFilter10;
            } else if (SweetCameraActivity.imgId == 10) {
                GPUImageTwoInputFilter gPUImageTwoInputFilter11 = (GPUImageTwoInputFilter) cls.newInstance();
                gPUImageTwoInputFilter11.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.img_blender_10));
                return gPUImageTwoInputFilter11;
            } else if (SweetCameraActivity.imgId == 11) {
                GPUImageTwoInputFilter gPUImageTwoInputFilter12 = (GPUImageTwoInputFilter) cls.newInstance();
                gPUImageTwoInputFilter12.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.img_blender_11));
                return gPUImageTwoInputFilter12;
            } else if (SweetCameraActivity.imgId == 12) {
                GPUImageTwoInputFilter gPUImageTwoInputFilter13 = (GPUImageTwoInputFilter) cls.newInstance();
                gPUImageTwoInputFilter13.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.img_blender_12));
                return gPUImageTwoInputFilter13;
            } else if (SweetCameraActivity.imgId == 13) {
                GPUImageTwoInputFilter gPUImageTwoInputFilter14 = (GPUImageTwoInputFilter) cls.newInstance();
                gPUImageTwoInputFilter14.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.img_blender_13));
                return gPUImageTwoInputFilter14;
            } else if (SweetCameraActivity.imgId == 14) {
                GPUImageTwoInputFilter gPUImageTwoInputFilter15 = (GPUImageTwoInputFilter) cls.newInstance();
                gPUImageTwoInputFilter15.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.img_blender_14));
                return gPUImageTwoInputFilter15;
            } else if (SweetCameraActivity.imgId == 15) {
                GPUImageTwoInputFilter gPUImageTwoInputFilter16 = (GPUImageTwoInputFilter) cls.newInstance();
                gPUImageTwoInputFilter16.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.img_blender_15));
                return gPUImageTwoInputFilter16;
            } else if (SweetCameraActivity.imgId == 16) {
                GPUImageTwoInputFilter gPUImageTwoInputFilter17 = (GPUImageTwoInputFilter) cls.newInstance();
                gPUImageTwoInputFilter17.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.img_blender_16));
                return gPUImageTwoInputFilter17;
            } else {
                GPUImageTwoInputFilter gPUImageTwoInputFilter18 = (GPUImageTwoInputFilter) cls.newInstance();
                gPUImageTwoInputFilter18.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.img_noanyfilter));
                return gPUImageTwoInputFilter18;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static GPUImageFilter createGradientFilter(Context context, Class<? extends GPUImageTwoInputFilter> cls) {
        try {
            if (SweetCameraActivity.imgId == 0) {
                GPUImageTwoInputFilter gPUImageTwoInputFilter = (GPUImageTwoInputFilter) cls.newInstance();
                gPUImageTwoInputFilter.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.img_noanyfilter));
                return gPUImageTwoInputFilter;
            } else if (SweetCameraActivity.imgId == 1) {
                GPUImageTwoInputFilter gPUImageTwoInputFilter2 = (GPUImageTwoInputFilter) cls.newInstance();
                gPUImageTwoInputFilter2.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.img_gradient_01));
                return gPUImageTwoInputFilter2;
            } else if (SweetCameraActivity.imgId == 2) {
                GPUImageTwoInputFilter gPUImageTwoInputFilter3 = (GPUImageTwoInputFilter) cls.newInstance();
                gPUImageTwoInputFilter3.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.img_gradient_02));
                return gPUImageTwoInputFilter3;
            } else if (SweetCameraActivity.imgId == 3) {
                GPUImageTwoInputFilter gPUImageTwoInputFilter4 = (GPUImageTwoInputFilter) cls.newInstance();
                gPUImageTwoInputFilter4.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.img_gradient_03));
                return gPUImageTwoInputFilter4;
            } else if (SweetCameraActivity.imgId == 4) {
                GPUImageTwoInputFilter gPUImageTwoInputFilter5 = (GPUImageTwoInputFilter) cls.newInstance();
                gPUImageTwoInputFilter5.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.img_gradient_04));
                return gPUImageTwoInputFilter5;
            } else if (SweetCameraActivity.imgId == 5) {
                GPUImageTwoInputFilter gPUImageTwoInputFilter6 = (GPUImageTwoInputFilter) cls.newInstance();
                gPUImageTwoInputFilter6.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.img_gradient_05));
                return gPUImageTwoInputFilter6;
            } else if (SweetCameraActivity.imgId == 6) {
                GPUImageTwoInputFilter gPUImageTwoInputFilter7 = (GPUImageTwoInputFilter) cls.newInstance();
                gPUImageTwoInputFilter7.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.img_gradient_06));
                return gPUImageTwoInputFilter7;
            } else if (SweetCameraActivity.imgId == 7) {
                GPUImageTwoInputFilter gPUImageTwoInputFilter8 = (GPUImageTwoInputFilter) cls.newInstance();
                gPUImageTwoInputFilter8.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.img_gradient_07));
                return gPUImageTwoInputFilter8;
            } else if (SweetCameraActivity.imgId == View.GONE) {
                GPUImageTwoInputFilter gPUImageTwoInputFilter9 = (GPUImageTwoInputFilter) cls.newInstance();
                gPUImageTwoInputFilter9.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.img_gradient_08));
                return gPUImageTwoInputFilter9;
            } else if (SweetCameraActivity.imgId == 9) {
                GPUImageTwoInputFilter gPUImageTwoInputFilter10 = (GPUImageTwoInputFilter) cls.newInstance();
                gPUImageTwoInputFilter10.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.img_gradient_09));
                return gPUImageTwoInputFilter10;
            } else if (SweetCameraActivity.imgId == 10) {
                GPUImageTwoInputFilter gPUImageTwoInputFilter11 = (GPUImageTwoInputFilter) cls.newInstance();
                gPUImageTwoInputFilter11.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.img_gradient_10));
                return gPUImageTwoInputFilter11;
            } else if (SweetCameraActivity.imgId == 11) {
                GPUImageTwoInputFilter gPUImageTwoInputFilter12 = (GPUImageTwoInputFilter) cls.newInstance();
                gPUImageTwoInputFilter12.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.img_gradient_11));
                return gPUImageTwoInputFilter12;
            } else if (SweetCameraActivity.imgId == 12) {
                GPUImageTwoInputFilter gPUImageTwoInputFilter13 = (GPUImageTwoInputFilter) cls.newInstance();
                gPUImageTwoInputFilter13.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.img_gradient_12));
                return gPUImageTwoInputFilter13;
            } else if (SweetCameraActivity.imgId == 13) {
                GPUImageTwoInputFilter gPUImageTwoInputFilter14 = (GPUImageTwoInputFilter) cls.newInstance();
                gPUImageTwoInputFilter14.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.img_gradient_13));
                return gPUImageTwoInputFilter14;
            } else if (SweetCameraActivity.imgId == 14) {
                GPUImageTwoInputFilter gPUImageTwoInputFilter15 = (GPUImageTwoInputFilter) cls.newInstance();
                gPUImageTwoInputFilter15.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.img_gradient_14));
                return gPUImageTwoInputFilter15;
            } else if (SweetCameraActivity.imgId == 15) {
                GPUImageTwoInputFilter gPUImageTwoInputFilter16 = (GPUImageTwoInputFilter) cls.newInstance();
                gPUImageTwoInputFilter16.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.img_gradient_15));
                return gPUImageTwoInputFilter16;
            } else if (SweetCameraActivity.imgId == 16) {
                GPUImageTwoInputFilter gPUImageTwoInputFilter17 = (GPUImageTwoInputFilter) cls.newInstance();
                gPUImageTwoInputFilter17.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.img_gradient_16));
                return gPUImageTwoInputFilter17;
            } else {
                GPUImageTwoInputFilter gPUImageTwoInputFilter18 = (GPUImageTwoInputFilter) cls.newInstance();
                gPUImageTwoInputFilter18.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.img_noanyfilter));
                return gPUImageTwoInputFilter18;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
