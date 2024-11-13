#ifndef COMMON_H
#define COMMON_H

#include <chrono>
#include <ctime>
#include <iomanip>

#ifdef DEBUG
    #define LOG(msg) do { \
        std::time_t now = std::time(nullptr); \
        std::cout << "[DEBUG] [" << std::put_time(std::localtime(&now), "%Y-%m-%d %H:%M:%S") \
        << "] "  << __FILE__ << ":" << __LINE__ << " (" << __func__ << ") - " << msg << std::endl << std::flush ; \
    } while(0)

#else
    #define LOG(msg) // No-op in release builds
#endif


#endif