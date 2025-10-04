#pragma once
#include <opencv2/opencv.hpp>
#include <iostream>
#include <vector>
#include <algorithm>
#include <filesystem>
#include <cmath>

using namespace cv;
using namespace std;

// Fonction pour calculer l'histogramme d'une image en niveaux de gris
void calculerHistogrammeGrayscale(const Mat& image, vector<int>& histogramme) {
    fill(histogramme.begin(), histogramme.end(), 0);

    int lignes = image.rows;
    int colonnes = image.cols;

    for (int x = 0; x < lignes; x++) {
        for (int y = 0; y < colonnes; y++) {
            int intensity = image.at<uchar>(x, y);
            if (intensity >= 0 && intensity < 256) {
                histogramme[intensity]++;
            }
        }
    }
}

// affichage de l'histogramme
Mat afficherHistogramme(const vector<int>& histogramme) {
    int histSize = 256;  
    int hist_w = 800, hist_h = 600;  
    int bin_w = cvRound((double)hist_w / histSize);  
    int margin = 50; 
    Mat histImage(hist_h + margin, hist_w + margin, CV_8UC1, Scalar(255));
    int max_val = *max_element(histogramme.begin(), histogramme.end());
    vector<int> hist_normalized(histSize);
    for (int i = 0; i < histSize; i++) {
        hist_normalized[i] = cvRound((double)histogramme[i] * (hist_h - margin) / max_val);
    }

    for (int i = 0; i <= 10; i++) {
        int y = hist_h - (i * (hist_h - margin) / 10);
        line(histImage, Point(margin, y), Point(hist_w + margin - 10, y),
             Scalar(200, 200, 200), 1, LINE_AA);
        string ylabel = to_string(i * max_val / 10);
        putText(histImage, ylabel, Point(5, y + 5),
                FONT_HERSHEY_SIMPLEX, 0.4, Scalar(0, 0, 0), 1, LINE_AA);
    }

    for (int i = 0; i <= 10; i++) {
        int x = margin + (i * (hist_w - margin) / 10);
        line(histImage, Point(x, margin), Point(x, hist_h),
             Scalar(200, 200, 200), 1, LINE_AA);
        string xlabel = to_string(i * 255 / 10);
        putText(histImage, xlabel, Point(x - 10, hist_h + margin - 10),
                FONT_HERSHEY_SIMPLEX, 0.4, Scalar(0, 0, 0), 1, LINE_AA);
    }

    line(histImage, Point(margin, hist_h), Point(hist_w + margin - 10, hist_h),
         Scalar(0, 0, 0), 2, LINE_AA);  
    line(histImage, Point(margin, margin), Point(margin, hist_h),
         Scalar(0, 0, 0), 2, LINE_AA);  

    putText(histImage, "Intensite", Point(hist_w / 2, hist_h + margin - 10),
            FONT_HERSHEY_SIMPLEX, 0.7, Scalar(0, 0, 0), 2, LINE_AA);
    putText(histImage, "Frequence", Point(5, margin - 10),
            FONT_HERSHEY_SIMPLEX, 0.7, Scalar(0, 0, 0), 2, LINE_AA);

    for (int i = 0; i < histSize; i++) {
        rectangle(histImage,
                  Point(bin_w * i + margin, hist_h),
                  Point(bin_w * (i + 1) + margin - 1, hist_h - hist_normalized[i]),
                  Scalar(0),  
                  FILLED); 
    }

    imshow("Histogramme", histImage);
    waitKey(0);
    destroyAllWindows();
    return histImage;
}
