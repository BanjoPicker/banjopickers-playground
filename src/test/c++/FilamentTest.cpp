#include<iostream>
#include"Filament.hpp"

/* Global Variable to test for preemption: */
int pCurrent;

class MyFilament : public banjopicker::Filament {
    public:
        MyFilament(int _id) : Filament(_id), EXIT_FILAMENT(false) { }

        public: bool EXIT_FILAMENT;

        void main(void) {
            for(;;) {
                pCurrent = id;
                for(unsigned volatile Delay = 0; Delay < 10000000*(id +1); ++Delay) { ; }
                if(pCurrent != id) {
                    printf("**** Preemption Failed! %d Current %d\n", id, pCurrent);
                }
                this->yield();
                if(EXIT_FILAMENT) {
                    break;
                }
            }
            printf("Exiting filament %d\n", id);
        }
};  /* class MyFilament */

class MyOtherFilament : public banjopicker::Filament {
    public:
        MyOtherFilament(int _id) : Filament(_id), EXIT_FILAMENT(false) { }

        public: bool EXIT_FILAMENT;

        void main(void) {
            for(;;) {
                printf("MyOtherFilament %d\n", id);
                this->sleep(2);
                for(unsigned volatile Delay = 0; Delay < 300000000; ++Delay) { ; }
                this->yield();
                if(EXIT_FILAMENT) {
                    break;
                }
            }
            printf("Exiting filament %d\n", id);
        }
};  /* class MyOtherFilament */

int main(int argc, char* argv[]) {
    MyFilament a(0), b(1), c(2);
    MyOtherFilament d(3), e(4), f(5);
    std::cout << "Starting a.start() = " << a.start() << std::endl;
    std::cout << "Starting b.start() = " << b.start() << std::endl;
    std::cout << "Starting c.start() = " << c.start() << std::endl;
    std::cout << "Starting d.start() = " << d.start() << std::endl;
    std::cout << "Starting e.start() = " << e.start() << std::endl;
    std::cout << "Starting f.start() = " << f.start() << std::endl;
    sleep(10*1);
    //banjopicker::Filament::Lock();
    printf("Main thread exiting the filaments\n");
    a.EXIT_FILAMENT = true;
    b.EXIT_FILAMENT = true;
    c.EXIT_FILAMENT = true;
    d.EXIT_FILAMENT = true;
    e.EXIT_FILAMENT = true;
    f.EXIT_FILAMENT = true;
    //banjopicker::Filament::Unlock();
    sleep(3);
    printf("Exiting main \n");
    return 0;
}
