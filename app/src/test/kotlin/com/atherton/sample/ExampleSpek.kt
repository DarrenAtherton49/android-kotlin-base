package com.atherton.sample

import org.assertj.core.api.Assertions.assertThat
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature

class ExampleSpek : Spek({

    val calculator = { x: Int, y: Int -> x + y }

    Feature("Calculator") {

        Scenario("Addition") {

            var input1 = 0
            var input2 = 0
            Given("two valid inputs") {
                input1 = 2
                input2 = 2
            }

            var output = 0
            When("we add the inputs together") {
                output = calculator.invoke(input1, input2)
            }

            Then("we should get the correct result") {
                assertThat(output).isEqualTo(4)
            }
        }
    }
})
