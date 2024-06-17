module contains_usage_scoped_fail;

import contains_declarations_scoped_fail : a_declaration_you_are_meant_to_see;

void main(){
    <ref>a_declaration_your_not_meant_to_see();
}
