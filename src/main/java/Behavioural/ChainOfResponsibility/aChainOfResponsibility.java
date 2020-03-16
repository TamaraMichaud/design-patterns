package Behavioural.ChainOfResponsibility;

// a sequence of handlers processing an event one after the other
// usually chaings go uphill; i.e. in a company, employee messes up; who's fault; he's corrupt? his boss? CEO is dodgy?

// click a graphical element on a form:
// button handles the event
// container may require a refresh,
// window might do something too... etc upwards

// Command Query Separation (not sure the pertinence of this note but good to have vocabulary)
// whenever we operate on objects, we separate these operations into:
// - query:   asking for information. e.g. please tell me your attack value
// - command: asking for an action or change. e.g. please set your attack value to 2
// CQS = having separate means of sending commands and queries to enable e.g. directly accessing a field


public class aChainOfResponsibility {
}

