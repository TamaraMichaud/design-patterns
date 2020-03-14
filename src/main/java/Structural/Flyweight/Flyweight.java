package Structural.Flyweight;

// space optimisation!
// avoid redundancy when storing data
// e.g. mmorpg -> lots of people with the same name. no point storing them all separately;
//      simply store a list of unique names and a pointer to the name in question (or index for that name)
// e.g. bold/italic text -> you don't care about each individual character that needs to be made bold/italic,
//      simply the start-end points in the string

// a space optimisation technique where we store externally the data associated with similar objects

// whenever you have data repeating, you store it externally and specify an index to that store
// define the idea of a "range" on homogeneous collections and store data related to those ranges


public class Flyweight {
}
