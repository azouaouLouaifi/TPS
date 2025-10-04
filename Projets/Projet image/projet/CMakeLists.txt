cmake_minimum_required(VERSION 3.0)
project(LectureImage)

find_package(OpenCV REQUIRED)
include_directories(${OpenCV_INCLUDE_DIRS})

add_executable(lecture_image main.cpp)
target_link_libraries(lecture_image ${OpenCV_LIBS}) 