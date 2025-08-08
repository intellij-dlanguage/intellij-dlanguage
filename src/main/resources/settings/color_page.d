#!/usr/bin/env dub
/*
 * Some block commments
 * go here
 */

/+
 + Nesting comment
 +/

/// A single line documentation comment
module <MODULE_DEFINITION>net.masterthought.cucumber.report_information</MODULE_DEFINITION>;

import std.algorithm;
import std.array;
import std.string;
import std.conv : to; // line comment about this import

import jsonizer.tojson;
import net.masterthought.cucumber.report_parser;

/**
 * A documentation comment
 */
<AT_ATTRIBUTE>@safe</AT_ATTRIBUTE>:
class ReportInformation {

    deprecated <KEYWORD>string</KEYWORD> originalId = "Some text\twith a gap";

    <KEYWORD>string</KEYWORD> runId = `Some \n Text`;

    <KEYWORD>string</KEYWORD>  anotherId = "\&para;\U0001F603"; // ¶😃

    Feature[] features;

    private auto symbol = '£';

    private <KEYWORD>string</KEYWORD> m_name;
    <AT_ATTRIBUTE>@property</AT_ATTRIBUTE> <KEYWORD>string</KEYWORD> <FUNCTION_DEFINITION>name</FUNCTION_DEFINITION>() { return m_name; }

    this(ReportParser parser) {
        this.runId = parser.<FUNCTION_CALL>getRunId</FUNCTION_CALL>();
        this.features = parser.<FUNCTION_CALL>getReports</FUNCTION_CALL>().<FUNCTION_CALL>map</FUNCTION_CALL>!(report => report.<FUNCTION_CALL>getFeatures</FUNCTION_CALL>()).<FUNCTION_CALL>joiner</FUNCTION_CALL>.<FUNCTION_CALL>array</FUNCTION_CALL>;

        writeln(__VENDOR__);
    }

    private Feature[] <FUNCTION_DEFINITION>processFeatures</FUNCTION_DEFINITION>(Feature[] features) {
        return features.<FUNCTION_CALL>map</FUNCTION_CALL>!((f) {
            f.featureInformation = <FUNCTION_CALL>calculateFeatureInformation</FUNCTION_CALL>(f);
            f.scenarios = <FUNCTION_CALL>addScenarioInformation</FUNCTION_CALL>(f);
            return f;
        }).<FUNCTION_CALL>array</FUNCTION_CALL>;
    }

    public auto <FUNCTION_DEFINITION>getTotalNumberOfBackgroundScenariosUnknown</FUNCTION_DEFINITION>() {
        return features.<FUNCTION_CALL>map</FUNCTION_CALL>!(f => f.<FUNCTION_CALL>getBackgroundScenariosUnknown</FUNCTION_CALL>().length).<FUNCTION_CALL>sum</FUNCTION_CALL>;
    }
}

struct {
    <KEYWORD>string</KEYWORD> name;
}

union {
    <KEYWORD>string</KEYWORD> day;
}

unittest {
    // load test json from file
    auto testJson = <FUNCTION_CALL>to</FUNCTION_CALL>!string(<FUNCTION_CALL>read</FUNCTION_CALL>("src/test/resources/project1.json"));
    <KEYWORD>string</KEYWORD> runId = "run 1";
    ReportInformation ri = new ReportInformation(new ReportParser(runId,[testJson]));

    assert (ri.name != null);
    // should have correct number of features
    ri.<FUNCTION_CALL>getFeatures()</FUNCTION_CALL>.length.<FUNCTION_CALL>assertEqual</FUNCTION_CALL>(2);

    // overall status
    ri.<FUNCTION_CALL>getOverallStatus</FUNCTION_CALL>.<FUNCTION_CALL>assertEqual(<FUNCTION_CALL>to</FUNCTION_CALL>!<KEYWORD>string</KEYWORD>(Status.Failed));

    // feature totals
    Feature feature = ri.<FUNCTION_CALL>getFeatures</FUNCTION_CALL>().<FUNCTION_CALL>front</FUNCTION_CALL>;

}

<TYPE_PARAMETER>T</TYPE_PARAMETER> <FUNCTION_DEFINITION>foo</FUNCTION_DEFINITION>(<TYPE_PARAMETER>T</TYPE_PARAMETER>, <TYPE_PARAMETER>E</TYPE_PARAMETER> : Exception)(Node node, in <KEYWORD>string</KEYWORD> path) {
    return node.<FUNCTION_CALL>bar</FUNCTION_CALL>!(<TYPE_PARAMETER>T</TYPE_PARAMETER>, <TYPE_PARAMETER>E</TYPE_PARAMETER>)(path);
}
