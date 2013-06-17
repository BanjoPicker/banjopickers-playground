#include<unistd.h>
#include<pthread.h>
#include<sched.h>


/**
 *  <p>This is an expiremental test class to see if an idea would work out.</p>
 */

namespace banjopicker {

class Filament {

    public: const int id;

    public: Filament(int id) : id(id) { }

    public: virtual ~Filament() { }

    /**
     *  <p>Start this thread.</p>
     *
     *  <p> 
     *      Usage:  implement a subclass of the filement and implement the main method.  Then, create an instance and call instance.start()
     *              actually run it.
                    Filament* filement = new MyFilament();
                    filament->start();
     *  </p>
     */
    public: 
        int start(void) {
            return pthread_create(&thread,NULL,&Filament::run, this);
        }

    protected:
        virtual void main(void) = 0;

    protected:

        pthread_t thread;

        void yield() {
            this->Unlock();
            //pthread_yield();  // for linux, not for macos
            //sched_yield();  // for macos
            this->Lock();
        };

        /* 
            Wrapper calls around the (possibly) blocking system calls to let filaments give up the runnable mutex before entering system calls.
            This facilitates allowing a filement to call a socket (netowrk or can) read or write without tying up the other filaments. 
        */
        int write(int fd, void* const buffer, size_t count) {
            int rc;
            Filament::Unlock();
            rc = ::write(fd, buffer, count);
            Filament::Lock();
            return rc;
        }

        int read(int fd, void* buffer, size_t count) {
            int rc;
            Filament::Unlock();
            rc = ::read(fd, buffer, count);
            Filament::Lock();
            return rc;
        }

        unsigned int sleep(unsigned int sleeptime) {
            int rc;
            Filament::Unlock();
            rc = ::sleep(sleeptime);
            Filament::Lock();
            return rc;
        }

        /**
         *  <p>The function for the pthread create to run.  You pass in a pointer to an instace of a Filament to get it going.</p>
         */
        static void* run(void* filament_instance) {
            if(filament_instance == NULL) {
                return NULL; 
            } else {
                Filament* filament = reinterpret_cast<Filament*>(filament_instance);
                filament->Lock();
                filament->main();
                filament->Unlock();
                return NULL;
            }
        }

        protected: static pthread_mutex_t RunnableMutex;

        public: int Lock() { 
            int rc = pthread_mutex_lock(&Filament::RunnableMutex);
            printf("%d Lock() = %d\n", id, rc);
            return rc;
        };

        public: int Unlock() { 
            int rc = pthread_mutex_unlock(&Filament::RunnableMutex);
            printf("%d Unlock() = %d\n", id, rc);
            return rc;
        };

}; /* class Filament */

}; /* namespace banjopicker */


// this should be in a library instead, but for a test program this is fine
pthread_mutex_t banjopicker::Filament::RunnableMutex = PTHREAD_MUTEX_INITIALIZER;
