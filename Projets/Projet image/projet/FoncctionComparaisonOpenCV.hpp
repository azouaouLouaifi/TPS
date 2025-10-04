#include <opencv2/opencv.hpp>
using namespace cv;

Mat appliquerFiltreMedian(const Mat& image, int tailleKernel) {
    Mat result;
    medianBlur(image, result, tailleKernel);
    return result;
}

Mat appliquerFiltreMoyenneur(const Mat& image, int tailleKernel) {
    Mat result;
    blur(image, result, Size(tailleKernel, tailleKernel));
    return result;
}

Mat appliquerSobel(const Mat& image, int tailleKernel = 3) {
    Mat gray, gradX, gradY, absGradX, absGradY, result;
    cvtColor(image, gray, COLOR_BGR2GRAY);

    Sobel(gray, gradX, CV_16S, 1, 0, tailleKernel);
    Sobel(gray, gradY, CV_16S, 0, 1, tailleKernel);

    convertScaleAbs(gradX, absGradX);
    convertScaleAbs(gradY, absGradY);

    addWeighted(absGradX, 0.5, absGradY, 0.5, 0, result);
    return result;
}

Mat appliquerLaplacien(const Mat& image, int tailleKernel = 3) {
    Mat gray, result;
    cvtColor(image, gray, COLOR_BGR2GRAY);
    Laplacian(gray, result, CV_16S, tailleKernel);
    convertScaleAbs(result, result);
    return result;
}

Mat appliquerFlouGaussien(const Mat& image, int tailleKernel, double sigmaX, double sigmaY = 0.0) {
    Mat result;
    GaussianBlur(image, result, Size(tailleKernel, tailleKernel), sigmaX, sigmaY);
    return result;
}