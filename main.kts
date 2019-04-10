// Explore a simple class

println("UW Homework: Simple Kotlin")

// write a "whenFn" that takes an arg of type "Any" and returns a String
fun whenFn(input: Any): String {
    when (input) {
        "Hello" -> return "world"
        is String -> return "Say what?"
        0 -> return "zero"
        1 -> return "one"
        in 2..10 -> return "low number"
        is Int -> return "a number"
        else -> return "I don't understand"
    }
}

// write an "add" function that takes two Ints, returns an Int, and adds the values
fun add(n1:Int, n2:Int): Int {
    return n1+n2
}

// write a "sub" function that takes two Ints, returns an Int, and subtracts the values
fun sub(n1:Int, n2:Int): Int {
    return n1-n2
}

// write a "mathOp" function that takes two Ints and a function (that takes two Ints and returns an Int), returns an Int, and applies the passed-in-function to the arguments
fun mathOp(n1:Int, n2:Int, func:(Int,Int)->Int): Int {
    return func(n1,n2)
}

// write a class "Person" with first name, last name and age
class Person(var firstName: String, var lastName: String, var age: Int) {
    // properties, constructor, equals, hashcode, debugString property all needed
    public val debugString: String
        get() = "[Person firstName:${firstName} lastName:${lastName} age:${age}]"

    fun equals(other: Person): Boolean {
        return this.hashCode() == other.hashCode()
    }

    override fun hashCode(): Int {
        return firstName.hashCode() * 17 + lastName.hashCode() * 3 + age.hashCode() * 53
    }
}

// write a class "Money"
data class Money(val amount: Int, val currency: String) {
    // need to account the correct types of currencies in the spec
    init {
        if (amount < 0) {
            throw Exception("Amount given was less than 0")
        }

        if (!currency.equals("USD") && !currency.equals("GBP") && !currency.equals("EUR") && !currency.equals("CAN")) {
            throw Exception("Cannot recognize given currency")
        }
    }

    fun convert(type: String): Money {
        // 10USD -> 5GBP
        // 10USD -> 15EUR
        // 12USD -> 15CAN
        if (this.currency.equals(type)) {
            return this
        } else {
            var amt = 0
            when (type) {
                "USD" -> when(this.currency) {
                    "GBP" -> amt = 10
                    "EUR" -> amt = 10
                    "CAN" -> amt = 12
                }
                "GBP" -> amt = 5
                "EUR" -> amt = 15
                "CAN" -> amt = 15
            }
            return Money(amt,type)
        }
    }

    operator fun plus(other: Money): Money {
        if (this.currency != other.currency) {
            return Money(this.amount + other.convert(this.currency).amount, this.currency)
        } else {
            return Money(this.amount + other.amount, this.currency)
        }
    }
}

// ============ DO NOT EDIT BELOW THIS LINE =============

print("When tests: ")
val when_tests = listOf(
    "Hello" to "world",
    "Howdy" to "Say what?",
    "Bonjour" to "Say what?",
    0 to "zero",
    1 to "one",
    5 to "low number",
    9 to "low number",
    17.0 to "I don't understand"
)
for ((k,v) in when_tests) {
    print(if (whenFn(k) == v) "." else "!")
}
println("")

print("Add tests: ")
val add_tests = listOf(
    Pair(0, 0) to 0,
    Pair(1, 2) to 3,
    Pair(-2, 2) to 0,
    Pair(123, 456) to 579
)
for ( (k,v) in add_tests) {
    print(if (add(k.first, k.second) == v) "." else "!")
}
println("")

print("Sub tests: ")
val sub_tests = listOf(
    Pair(0, 0) to 0,
    Pair(2, 1) to 1,
    Pair(-2, 2) to -4,
    Pair(456, 123) to 333
)
for ( (k,v) in sub_tests) {
    print(if (sub(k.first, k.second) == v) "." else "!")
}
println("")

print("Op tests: ")
print(if (mathOp(2, 2, { l,r -> l+r} ) == 4) "." else "!")
print(if (mathOp(2, 2, ::add ) == 4) "." else "!")
print(if (mathOp(2, 2, ::sub ) == 0) "." else "!")
print(if (mathOp(2, 2, { l,r -> l*r} ) == 4) "." else "!")
println("")


print("Person tests: ")
val p1 = Person("Ted", "Neward", 47)
print(if (p1.firstName == "Ted") "." else "!")
p1.age = 48
print(if (p1.debugString == "[Person firstName:Ted lastName:Neward age:48]") "." else "!")
println("")

print("Money tests: ")
val tenUSD = Money(10, "USD")
val twelveUSD = Money(12, "USD")
val fiveGBP = Money(5, "GBP")
val fifteenEUR = Money(15, "EUR")
val fifteenCAN = Money(15, "CAN")
val convert_tests = listOf(
    Pair(tenUSD, tenUSD),
    Pair(tenUSD, fiveGBP),
    Pair(tenUSD, fifteenEUR),
    Pair(twelveUSD, fifteenCAN),
    Pair(fiveGBP, tenUSD),
    Pair(fiveGBP, fifteenEUR)
)
for ( (from,to) in convert_tests) {
    print(if (from.convert(to.currency).amount == to.amount) "." else "!")
}
val moneyadd_tests = listOf(
    Pair(tenUSD, tenUSD) to Money(20, "USD"),
    Pair(tenUSD, fiveGBP) to Money(20, "USD"),
    Pair(fiveGBP, tenUSD) to Money(10, "GBP")
)
for ( (pair, result) in moneyadd_tests) {
    print(if ((pair.first + pair.second).amount == result.amount &&
              (pair.first + pair.second).currency == result.currency) "." else "!")
}
println("")
