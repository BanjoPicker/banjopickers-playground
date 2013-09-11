#!/usr/bin/rscript

# Fun with various distributions and functions related to distributions.

# Draw 5 samples of an exponetial random variable:
print(rexp(5))

# Draw5 weibulls with shape parameter 3:
print(rweibull(5,3));

# for each distribution DIST, there are functions 
#   dDIST = density function for the distribution
#   pDIST = cumulative distribution function i.e. pDIST(x) = Prob(X <= x) where X ~ DIST
#   qDIST = quartile function
#   rDIST = draw a random sample from DIST

# evaluate the density function for the exponential:
print(dexp(1))

# evaluate the cumulative distribution function for the exponential distribution:
print(pexp(1))
print(pexp(10))
print(pexp(100))
