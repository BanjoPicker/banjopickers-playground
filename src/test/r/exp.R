#!/usr/bin/rscript

# Set up some constants:
a <- 0
b <- 10
c <- 500

# Generate some x's:
x <- runif(c, a, b)
x <- rnorm(c, 5, 2)

# Now, we assume the lambda is a function of x: lambda = a + bx
lambda <- exp(-1.0 - 0.2*x)

# Now generate a sample 
y <- rexp(c, lambda)

# Plot the x,y pairs:
plot(x, y)

# Plot the lambdas vs. the x:
plot(x, lambda)
