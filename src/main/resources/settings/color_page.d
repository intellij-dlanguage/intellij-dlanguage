/*
 * Some block commments
 * go here
 */

/+
 + Nesting comment
 +/

// Line comment
module net.masterthought.cucumber.report_information;

import std.algorithm;
import std.array;

import jsonizer.tojson;
import net.masterthought.cucumber.report_parser;

class ReportInformation {
    string runId;
    Feature[] features;

    private string m_name;
    @property string name() { return m_name; }

    this(ReportParser parser) {
        this.runId = parser.getRunId();
        this.features = parser.getReports().map!(report => report.getFeatures()).joiner.array;
    }

    private Feature[] processFeatures(Feature[] features) {
        return features.map!((f) {
            f.featureInformation = calculateFeatureInformation(f);
            f.scenarios = addScenarioInformation(f);
            return f;
        }).array;
    }

    public auto getTotalNumberOfBackgroundScenariosUnknown() {
        return features.map!(f => f.getBackgroundScenariosUnknown().length).sum;
    }
}

struct {
    string name;
}

union {
    string day;
}

unittest {
    // load test json from file
    auto testJson = to!string(read("src/test/resources/project1.json"));
    string runId = "run 1";
    ReportInformation ri = new ReportInformation(new ReportParser(runId,[testJson]));

    // should have correct number of features
    ri.getFeatures().length.assertEqual(2);

    // overall status
    ri.getOverallStatus.assertEqual(to!string(Status.Failed));

    // feature totals
    Feature feature = ri.getFeatures().front;
}

T foo(T, E : Exception)(Node node, in string path) {
    return node.bar!(T, E)(path);
}
