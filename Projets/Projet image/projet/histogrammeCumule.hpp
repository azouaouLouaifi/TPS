#pragma once
#include <opencv2/opencv.hpp>
#include <iostream>
#include <vector>
#include <algorithm>
#include <filesystem>
#include <cmath>
using namespace cv;
using namespace std;

// Fonction pour calculer l'histogramme cumulé
void calculerHistogrammeCumule(const vector<int>& histogramme, vector<int>& histogrammeCumule) {
    histogrammeCumule[0] = histogramme[0];
    for (int i = 1; i < 256; i++) {
        histogrammeCumule[i] = histogrammeCumule[i - 1] + histogramme[i];
    }
}

// Fonction pour afficher l'histogramme cumulé
Mat afficherHistogrammeCumule(const vector<int>& histogrammeCumule) {
    int histSize = 256;  
    int hist_w = 800, hist_h = 600;  
    int bin_w = cvRound((double)hist_w / histSize); 
    int margin = 50;  
    Mat histImage(hist_h + margin, hist_w + margin, CV_8UC3, Scalar(255, 255, 255));
    int max_val = *max_element(histogrammeCumule.begin(), histogrammeCumule.end());
    vector<int> hist_normalized(histSize);
    for(int i = 0; i < histSize; i++) {
        hist_normalized[i] = cvRound((double)histogrammeCumule[i] * (hist_h - margin) / max_val);
    }
    for(int i = 0; i < histSize; i++) {
        rectangle(histImage,
                  Point(bin_w * i + margin, hist_h),
                  Point(bin_w * (i + 1) + margin - 1, hist_h - hist_normalized[i]),
                  Scalar(0, 0, 0), 
                  FILLED); 
    }

    line(histImage, Point(margin, hist_h), Point(hist_w + margin - 10, hist_h),
         Scalar(0, 0, 0), 2, LINE_AA);  
    line(histImage, Point(margin, margin), Point(margin, hist_h),
         Scalar(0, 0, 0), 2, LINE_AA);  

    putText(histImage, "Intensite", Point(hist_w / 2, hist_h + margin - 10),
            FONT_HERSHEY_SIMPLEX, 0.7, Scalar(0, 0, 0), 2, LINE_AA);
    putText(histImage, "Frequence", Point(5, margin - 10),
            FONT_HERSHEY_SIMPLEX, 0.7, Scalar(0, 0, 0), 2, LINE_AA);

    for (int i = 0; i <= 10; i++) {
        int y = hist_h - (i * (hist_h - margin) / 10);
        line(histImage, Point(margin - 5, y), Point(margin, y), Scalar(0, 0, 0), 1, LINE_AA);
        string ylabel = to_string(i * max_val / 10);
        putText(histImage, ylabel, Point(5, y + 5), FONT_HERSHEY_SIMPLEX, 0.4, Scalar(0, 0, 0), 1, LINE_AA);
    }

    for (int i = 0; i <= 10; i++) {
        int x = margin + (i * (hist_w - margin) / 10);
        line(histImage, Point(x, hist_h), Point(x, hist_h + 5), Scalar(0, 0, 0), 1, LINE_AA);
        string xlabel = to_string(i * 255 / 10);
        putText(histImage, xlabel, Point(x - 10, hist_h + margin - 10), FONT_HERSHEY_SIMPLEX, 0.4, Scalar(0, 0, 0), 1, LINE_AA);
    }

    imshow("Histogramme cumulé", histImage);
    waitKey(0);
    destroyAllWindows();
    return histImage;
}