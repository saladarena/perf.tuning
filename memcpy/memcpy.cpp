#include <iostream>
#include <cstring>
#include <chrono>
#include <ctime>

#include <vector>

std::vector<uint8_t*> generate_buffers(size_t buffer_size, int num_buffers) {
    std::vector<uint8_t*> buffers;

    for (int i = 0; i < num_buffers; ++i) {
        uint8_t* buffer = new uint8_t[buffer_size];

        buffers.push_back(buffer);
    }

    return buffers;
}

std::vector<int> generate_random_numbers(int number) {
    std::vector<int> random_numbers;
    srand(time(NULL));

    while (random_numbers.size() < number) {
        int random_num = rand() % number;
        random_numbers.push_back(random_num);
    }

    return random_numbers;
}
int main() {

    int buffer_size = 64;
    int buffer_number =  1024 * 256;

    auto dst_buffers = generate_buffers(64, buffer_number);
    auto src_buffers = generate_buffers(64, buffer_number);
    auto indices  = generate_random_numbers(buffer_number);


        // Measure the time taken for memcpy
    auto start = std::chrono::high_resolution_clock::now();
    for (int i = 0; i< buffer_number; i++) {
        std::memcpy(dst_buffers[indices[i]], src_buffers[indices[i]], buffer_size);
    }
    auto end = std::chrono::high_resolution_clock::now();

    // Calculate the duration in nanoseconds
    auto duration_ns = std::chrono::duration_cast<std::chrono::nanoseconds>(end - start);

    // Convert nanoseconds to milliseconds
    double duration_ms = duration_ns.count() / 1000000.0;

    // Print the duration in milliseconds
    std::cout << "Memory copy took " << duration_ms << " milliseconds" << std::endl;


    return 0;
}