#!/usr/bin/rscript

# Load the data library MASS
library("MASS")

# attach the Boston database so variable names can be resolved without having to specify them:
attach(Boston)

# Show the names of the columns:
names(Boston)

# The form for a linear regression is y~x which means y = a+bx 
# We can also do things like y~ x + I(x^2) to fit a quadratic funtion
lm.fit1 = lm(medv ~ poly(lstat,1), data = Boston)
lm.fit2 = lm(medv ~ poly(lstat,2), data = Boston)
lm.fit3 = lm(medv ~ poly(lstat,3), data = Boston)

print("Basic fit information:")
lm.fit1

print("More detailed fit information:")
print( summary( lm.fit1 ) )

# Look at what attributes are available in lm.fit:
print("Attributes in the fit variable:")
names(lm.fit1)

# Summary:
summary(lm.fit1)$sigma

# ANOVA table to compare models:
anova(lm.fit1, lm.fit2, lm.fit3)

#plot(lstat,medv)
#plot(lm.fit2)
#abline(lm.fit1)


# a general pattern:

# (1) generate some x's:
x <- rnorm(100, 5, 3)

# (2) generate the distribution parameters:
mean <- 1 + 0.5*x

# (3) generate the y's:
y <- rnorm(100, mean, 0.5)

# (4) plot the x,y combinations:
plot(x,y)
abline(lm(y~1+x))

# (5) linear regression:
lm(y~1+x+I(exp(x)))


