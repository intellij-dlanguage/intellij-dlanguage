
void foo()
{
    auto bar1 = () => 4;
    auto bar2 = ref () => 4;
    auto bar3 = auto ref () => 4;
    auto bar4 = function () => 4;
    auto bar5 = function ref () => 4;
    auto bar6 = function auto ref () => 4;
}
