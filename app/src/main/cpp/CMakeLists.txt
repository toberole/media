cmake_minimum_required(VERSION 3.4.1)


include_directories(${CMAKE_CURRENT_LIST_DIR}/include)

aux_source_directory(${CMAKE_CURRENT_LIST_DIR}/src SRC_FILES)

add_library(
        native-lib
        SHARED
        ${SRC_FILES})

find_library(log-lib log)

target_link_libraries(
        native-lib
        ${log-lib}
        android
        OpenSLES
)