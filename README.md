# A simple Java implementation of the ABAC system

## What is ABAC？
[ABAC(Attribute-based access control)](https://en.wikipedia.org/wiki/Attribute-based_access_control)
is a method of implementing access control policies that is highly adaptable and can be customized using a wide range of attributes, 
making it suitable for use in distributed or rapidly changing environments. 

The only limitations on the policies that can be implemented with ABAC are the capabilities of the computational language and the availability of relevant attributes.

ABAC policy rules are generated as Boolean functions of the subject's attributes, the object's attributes, and the environment attributes.

Now we use json to describe a group of policies. 

```json
[{
    "visitors":[
        { 
            "id":1
        }
    ],
    "context":{
        "rules":{
            "time":{
                "between":["11:30","15:20"]
            }
        }
    },
    "strategy":"approval"
}]
```

## How to use?
This project is currently under development.
There is an example for using abac-core.

```java
public class Main {
    public static void main(String[] args) {
        ABACFactory abacFactory = new ABACFactory();
        var list = List.of(RuleCreators.equal(), RuleCreators.between());
        abacFactory.getConfiguration().registerInformation(
                new Information(new PolicySource()
                        .setPolicySerialize(new GsonPolicySerialize())
                        .registerRuleCreators(list)
                        .setStrSource(() ->
                                """
                                        [{
                                           "visitors":[{
                                                "id":1
                                                },
                                                {
                                                "id":2
                                                },
                                                {
                                                "id":3
                                                }
                                           ],
                                           "context":{
                                               "rules":{
                                                   "time":{"between":["11:30","15:20"]}
                                               }
                                           },
                                           "strategy":"approval"
                                        }]"""
                        )));
        ABAC abac = abacFactory.create();
        Attribute attribute = new Attribute(
                new Visitor(1L, "1212", "name", Visitor.Role.root),
                new Option("name"),
                new Target("name", 12),
                new AttributeContext(Map.of("time", "11:20")));

        System.out.println(abac.enforce(attribute));
    }
}
```

> Maybe Kotlin is more suitable for this project and we will trying to refactor this project using Kotlin...
