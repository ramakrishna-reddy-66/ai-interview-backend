package com.interviewai.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class AIService {

    private final Map<String, List<String>> questionBank = new HashMap<>();
    private final Map<String, String> answerBank = new HashMap<>();

    public AIService() {
        initQuestionBank();
    }

    private void initQuestionBank() {
        // PYTHON
        questionBank.put("python", Arrays.asList(
            "What are the main features of Python? Explain with examples.",
            "What is the difference between a list and a tuple in Python?",
            "What are functions in Python? Explain with an example.",
            "What is object-oriented programming in Python? Explain classes, objects, and inheritance.",
            "What is the difference between == and is in Python?",
            "What are decorators in Python and how do they work?",
            "Explain Python generators and the yield keyword.",
            "How does memory management and garbage collection work in Python?"
        ));

        // JAVA
        questionBank.put("java", Arrays.asList(
            "What is OOP in Java? Explain the four main principles with examples.",
            "What is the difference between method overloading and method overriding in Java?",
            "What is inheritance in Java? Explain with an example.",
            "What is the difference between an interface and an abstract class in Java?",
            "What is exception handling in Java? Explain try, catch, and finally blocks.",
            "What is the difference between String, StringBuilder, and StringBuffer in Java?",
            "Explain the Java Collections Framework (List, Set, Map).",
            "What is the difference between HashMap and ConcurrentHashMap in Java?"
        ));

        // SPRING BOOT
        questionBank.put("spring", Arrays.asList(
            "What is Spring Boot and why is it used for backend development?",
            "What is dependency injection and Inversion of Control (IoC) in Spring Boot?",
            "What is a REST API in Spring Boot and how do you design endpoints?",
            "What is the purpose of @RestController, @Service, and @Repository annotations?",
            "What is Spring Data JPA and how does it simplify database operations?",
            "What is Spring Security and how does JWT authentication work?",
            "How do you handle global exceptions in Spring Boot?"
        ));

        // REACT
        questionBank.put("react", Arrays.asList(
            "What is React and how do functional components work?",
            "What is the difference between props and state in React?",
            "What is useState in React and how does state batching work?",
            "What is useEffect in React and when is cleanup required?",
            "What is the Virtual DOM and how does reconciliation work in React?",
            "What is the Context API in React and when would you use Redux instead?",
            "How do you optimize rendering performance in React (useMemo, useCallback, React.memo)?"
        ));

        // JAVASCRIPT
        questionBank.put("javascript", Arrays.asList(
            "What is the difference between var, let, and const in JavaScript?",
            "What is the JavaScript Event Loop, microtasks, and macrotasks?",
            "What are Closures in JavaScript and give a practical use case?",
            "What is the difference between Promises, async/await, and callbacks?",
            "What is prototype inheritance and how does the prototype chain work in JS?"
        ));

        // SQL & DBMS
        questionBank.put("sql", Arrays.asList(
            "What is the difference between WHERE and HAVING in SQL?",
            "What is the difference between DELETE, DROP, and TRUNCATE?",
            "What is a JOIN in SQL? Explain INNER, LEFT, RIGHT, and FULL joins.",
            "What is GROUP BY in SQL and how do aggregate functions work?",
            "What is a subquery in SQL and when should you use CTEs (Common Table Expressions)?"
        ));

        questionBank.put("dbms", Arrays.asList(
            "What is normalization in DBMS? Explain 1NF, 2NF, 3NF, and BCNF.",
            "What is a primary key, foreign key, and unique key in DBMS?",
            "What is the difference between SQL (relational) and NoSQL databases?",
            "What is a transaction in DBMS? Explain ACID properties.",
            "What is a database index (B-Tree/Hash) and how does it optimize queries?"
        ));

        // SYSTEM DESIGN
        questionBank.put("system design", Arrays.asList(
            "What is Load Balancing and what algorithms are commonly used?",
            "What is caching? Explain Redis, cache-aside, and cache eviction policies.",
            "Explain the CAP theorem and its implications in distributed systems.",
            "What is the difference between horizontal and vertical scaling?",
            "How would you design a URL shortener system like Bitly?"
        ));

        // DATA STRUCTURES & ALGORITHMS
        questionBank.put("dsa", Arrays.asList(
            "What is the difference between an Array and a Linked List in memory and performance?",
            "How does a Hash Map work internally and how are collisions handled?",
            "Explain Binary Search and its time complexity compared to Linear Search.",
            "What is the difference between Breadth-First Search (BFS) and Depth-First Search (DFS)?",
            "What is dynamic programming and how does memoization differ from tabulation?"
        ));

        // BEHAVIORAL & HR
        questionBank.put("behavioral", Arrays.asList(
            "Tell me about yourself and your background in software engineering.",
            "Describe a challenging technical problem you solved and how you approached it.",
            "How do you handle disagreement with a teammate or technical lead regarding architecture?",
            "Tell me about a time a production bug occurred and how you handled root-cause analysis.",
            "Where do you see yourself in the next 3 to 5 years in your career?"
        ));
    }

    public List<String> getAvailableTopics() {
        return Arrays.asList("Java", "Python", "JavaScript", "React", "Spring Boot", "SQL", "DBMS", "System Design", "DSA", "Behavioral");
    }

    public String generateQuestion(String topic, int questionNumber) {
        if (topic == null || topic.trim().isEmpty()) {
            topic = "general";
        }

        String lowerTopic = topic.toLowerCase();
        List<String> matched = null;

        for (Map.Entry<String, List<String>> entry : questionBank.entrySet()) {
            if (lowerTopic.contains(entry.getKey())) {
                matched = entry.getValue();
                break;
            }
        }

        if (matched != null && !matched.isEmpty()) {
            int index = Math.abs(questionNumber) % matched.size();
            return matched.get(index);
        }

        // Generic fallback questions
        String[] generic = {
            "Can you explain the core architecture and fundamental principles of " + topic + "?",
            "What are the best practices and common pitfalls when working with " + topic + "?",
            "How do you handle error handling, debugging, and performance optimization in " + topic + "?",
            "Can you provide a real-world project example where you implemented " + topic + "?",
            "What are the key security and scalability considerations in " + topic + "?"
        };

        return generic[Math.abs(questionNumber) % generic.length];
    }

    public String generateAnswer(String topic, String question) {
        if (question == null) return "";
        String lowerQuestion = question.toLowerCase();

        // Check specific keywords
        if (lowerQuestion.contains("main features of python")) {
            return "Python is a high-level, interpreted, dynamically typed programming language known for clean syntax. Key features include:\n"
                    + "1. Simple & Readable Syntax: Intuitive indentation and minimal boilerplate.\n"
                    + "2. Dynamically Typed: Variable types are inferred at runtime.\n"
                    + "3. Multi-Paradigm: Supports OOP, procedural, and functional programming.\n"
                    + "4. Rich Ecosystem: Huge standard library and packages for AI/ML (NumPy, PyTorch), Web (Django, FastAPI), and Automation.\n"
                    + "Example: `print('Hello World')` or list comprehensions `[x**2 for x in range(5)]`.";
        }

        if (lowerQuestion.contains("list and a tuple")) {
            return "Key differences between List and Tuple in Python:\n"
                    + "1. Mutability: Lists are mutable (elements can be modified/appended), whereas Tuples are immutable (cannot be changed after creation).\n"
                    + "2. Syntax: Lists use square brackets `[1, 2, 3]`, Tuples use parentheses `(1, 2, 3)`.\n"
                    + "3. Memory & Performance: Tuples consume less memory and have faster execution speed.\n"
                    + "4. Use Case: Use lists for dynamic collections and tuples for fixed data structures or dictionary keys.";
        }

        if (lowerQuestion.contains("functions in python")) {
            return "A function in Python is a reusable block of code defined with the `def` keyword.\n"
                    + "It accepts parameters, can specify default/keyword arguments (*args, **kwargs), and returns results with `return`.\n\n"
                    + "Example:\n"
                    + "def calculate_total(price, tax_rate=0.08):\n"
                    + "    return price * (1 + tax_rate)\n\n"
                    + "print(calculate_total(100)) # Output: 108.0";
        }

        if (lowerQuestion.contains("object-oriented") || lowerQuestion.contains("oop in java")) {
            return "Object-Oriented Programming (OOP) organizes software design around data and objects. The four pillars are:\n"
                    + "1. Encapsulation: Bundling data and methods within a class with access modifiers (private, public, protected).\n"
                    + "2. Inheritance: Deriving child classes from a parent class to promote code reuse (`extends` in Java, `class Child(Parent):` in Python).\n"
                    + "3. Polymorphism: Performing a single action in different ways through Method Overloading (compile-time) and Method Overriding (runtime).\n"
                    + "4. Abstraction: Hiding internal implementation details and exposing only essential interfaces.";
        }

        if (lowerQuestion.contains("overloading") && lowerQuestion.contains("overriding")) {
            return "Difference between Overloading and Overriding:\n"
                    + "• Method Overloading: Methods in the same class have the same name but different signatures (parameters count/types). Resolved at compile time.\n"
                    + "• Method Overriding: A subclass provides a specific implementation of a method already defined in its superclass using `@Override`. Resolved at runtime dynamically based on object instance.";
        }

        if (lowerQuestion.contains("interface") && lowerQuestion.contains("abstract class")) {
            return "Interface vs Abstract Class:\n"
                    + "• Interface: Defines a contract of behavior (`implements`). Can have default/static methods in Java 8+, supports multiple inheritance of type.\n"
                    + "• Abstract Class: Can maintain state (instance variables), constructors, and both abstract and concrete methods. A class can extend only one abstract class (`extends`).";
        }

        if (lowerQuestion.contains("exception handling")) {
            return "Exception handling manages runtime errors gracefully to maintain application flow:\n"
                    + "• `try`: Contains code that might throw an exception.\n"
                    + "• `catch`: Catches and handles specific exception types.\n"
                    + "• `finally`: Executes cleanup operations (closing streams/connections), always running whether an exception occurred or not.\n"
                    + "• `throw` / `throws`: Explicitly triggers or declares exceptions in method signatures.";
        }

        if (lowerQuestion.contains("spring boot") && (lowerQuestion.contains("backend") || lowerQuestion.contains("used for"))) {
            return "Spring Boot is an opinionated framework built on top of Spring that accelerates production-ready enterprise Java backends:\n"
                    + "1. Auto-Configuration: Automatically configures beans and components based on classpath dependencies.\n"
                    + "2. Embedded Web Server: Bundles Tomcat/Jetty/Undertow without needing external WAR deployments.\n"
                    + "3. Starters: Pre-packaged dependency descriptors (e.g., `spring-boot-starter-web`, `spring-boot-starter-data-jpa`).\n"
                    + "4. Actuator: Built-in production health metrics and monitoring endpoints.";
        }

        if (lowerQuestion.contains("dependency injection") || lowerQuestion.contains("ioc")) {
            return "Dependency Injection (DI) is a design pattern implementing Inversion of Control (IoC). Instead of objects instantiating their dependencies directly (`new Service()`), the Spring IoC Container manages lifecycle and injects required beans via Constructor Injection (recommended), Setter Injection, or Field Injection (`@Autowired`). This achieves loose coupling, testability, and modularity.";
        }

        if (lowerQuestion.contains("rest api")) {
            return "A REST (Representational State Transfer) API enables stateless client-server communication using standard HTTP methods:\n"
                    + "• GET: Retrieve resource\n"
                    + "• POST: Create new resource\n"
                    + "• PUT/PATCH: Replace/Update resource\n"
                    + "• DELETE: Remove resource\n"
                    + "Payloads are formatted in JSON/XML with standard status codes (200 OK, 201 Created, 400 Bad Request, 401 Unauthorized, 404 Not Found, 500 Error).";
        }

        if (lowerQuestion.contains("virtual dom")) {
            return "The Virtual DOM is an in-memory lightweight JavaScript representation of the real DOM tree. When state changes occur:\n"
                    + "1. React renders a new Virtual DOM tree.\n"
                    + "2. Diffing Algorithm compares the new tree with the previous Virtual DOM.\n"
                    + "3. Reconciliation batches and applies only the minimal necessary DOM updates to the actual browser DOM, boosting rendering efficiency.";
        }

        if (lowerQuestion.contains("props and state")) {
            return "Props vs State in React:\n"
                    + "• Props (Properties): Read-only data passed downwards from parent to child component to configure it.\n"
                    + "• State: Mutable, local data maintained inside the component that triggers a re-render whenever updated using `setState` or `useState` updater function.";
        }

        if (lowerQuestion.contains("where and having")) {
            return "WHERE vs HAVING in SQL:\n"
                    + "• `WHERE`: Filters individual table rows BEFORE any grouping or aggregation takes place.\n"
                    + "• `HAVING`: Filters summarized groups AFTER the `GROUP BY` clause has aggregated the records, allowing filtering based on aggregate functions (e.g., `HAVING COUNT(*) > 5`).";
        }

        if (lowerQuestion.contains("join")) {
            return "SQL JOIN types combine rows from two or more tables based on a related column:\n"
                    + "• INNER JOIN: Returns records with matching values in both tables.\n"
                    + "• LEFT (OUTER) JOIN: Returns all records from the left table and matched records from the right table (NULL if no match).\n"
                    + "• RIGHT (OUTER) JOIN: Returns all records from the right table and matched left records.\n"
                    + "• FULL (OUTER) JOIN: Returns all records when there is a match in either table.";
        }

        if (lowerQuestion.contains("normalization")) {
            return "Normalization is the process of organizing database schema to eliminate redundancy and maintain data integrity:\n"
                    + "• 1NF: Atomic values (no repeating groups/arrays).\n"
                    + "• 2NF: In 1NF and all non-key attributes fully depend on the primary key (no partial dependency).\n"
                    + "• 3NF: In 2NF and no transitive dependency (non-key columns do not depend on other non-key columns).\n"
                    + "• BCNF: Stronger 3NF where every determinant is a candidate key.";
        }

        if (lowerQuestion.contains("acid")) {
            return "ACID properties ensure reliable database transactions:\n"
                    + "• Atomicity: All operations in a transaction succeed completely or none do (All-or-Nothing).\n"
                    + "• Consistency: Transactions transition the database from one valid state to another, preserving constraints.\n"
                    + "• Isolation: Concurrent transactions execute independently without interfering with each other.\n"
                    + "• Durability: Once committed, changes survive system failures and power outages.";
        }

        if (lowerQuestion.contains("load balancing")) {
            return "A Load Balancer distributes incoming network traffic across multiple backend servers to ensure high availability, fault tolerance, and responsiveness. Common algorithms include Round Robin, Least Connections, IP Hash, and Weighted Round Robin.";
        }

        return "A comprehensive and professional answer for: \"" + question + "\"\n\n"
                + "Key Technical Concepts to Cover:\n"
                + "1. Core Definition & Architecture: Clearly state the purpose, lifecycle, and underlying mechanisms.\n"
                + "2. Practical Implementation: Highlight specific syntax, code structures, or design patterns.\n"
                + "3. Trade-offs & Edge Cases: Discuss performance implications, memory usage, and error handling.\n"
                + "4. Production Best Practices: Mention logging, testing, and security considerations.";
    }

    public String scoreAnswer(String question, String answer) {
        if (answer == null || answer.trim().isEmpty()) {
            return "Score: 0/10\nFeedback: No answer was provided. Please explain the core concepts with examples.";
        }

        String lowerQuestion = question != null ? question.toLowerCase() : "";
        String trimmedAnswer = answer.trim();
        String lowerAnswer = trimmedAnswer.toLowerCase();

        int score = 0;
        List<String> strengths = new ArrayList<>();
        List<String> tips = new ArrayList<>();

        // Base length & depth scoring
        int wordCount = trimmedAnswer.split("\\s+").length;
        if (wordCount >= 40) {
            score += 3;
            strengths.add("Good depth and detail in explanation");
        } else if (wordCount >= 20) {
            score += 2;
        } else if (wordCount >= 8) {
            score += 1;
        } else {
            tips.add("Answer is quite brief; provide more technical depth");
        }

        // Check for code / syntax / examples
        if (containsAny(lowerAnswer, "example", "for instance", "e.g.", "code", "def ", "class ", "public ", "function", "const ", "let ", "select ", "return", "import ", "()", "=>")) {
            score += 2;
            strengths.add("Includes relevant practical examples/syntax");
        } else {
            tips.add("Include practical code snippets or real-world examples");
        }

        // Domain-specific keyword matching
        if (lowerQuestion.contains("python") || lowerQuestion.contains("tuple") || lowerQuestion.contains("list")) {
            if (containsAny(lowerAnswer, "mutable", "immutable", "interpreted", "dynamic", "object", "syntax", "brackets", "parentheses", "memory", "generator", "yield")) {
                score += 3;
            }
        } else if (lowerQuestion.contains("java") || lowerQuestion.contains("oop") || lowerQuestion.contains("spring")) {
            if (containsAny(lowerAnswer, "encapsulation", "inheritance", "polymorphism", "abstraction", "interface", "class", "annotation", "ioc", "dependency injection", "spring", "bean", "jpa", "exception")) {
                score += 3;
            }
        } else if (lowerQuestion.contains("react") || lowerQuestion.contains("dom") || lowerQuestion.contains("hook")) {
            if (containsAny(lowerAnswer, "state", "props", "virtual dom", "reconciliation", "render", "hook", "usestate", "useeffect", "component", "re-render")) {
                score += 3;
            }
        } else if (lowerQuestion.contains("sql") || lowerQuestion.contains("dbms") || lowerQuestion.contains("database") || lowerQuestion.contains("acid")) {
            if (containsAny(lowerAnswer, "table", "query", "group by", "having", "join", "index", "acid", "atomicity", "consistency", "isolation", "durability", "primary key", "foreign key", "normalization")) {
                score += 3;
            }
        } else if (lowerQuestion.contains("system design") || lowerQuestion.contains("load balance") || lowerQuestion.contains("cache")) {
            if (containsAny(lowerAnswer, "scale", "horizontal", "vertical", "redis", "cap theorem", "latency", "throughput", "redundancy", "distributed", "sharding", "replica")) {
                score += 3;
            }
        } else {
            if (containsAny(lowerAnswer, "because", "architecture", "performance", "pattern", "structure", "lifecycle", "mechanism", "advantage", "trade-off")) {
                score += 3;
            }
        }

        // Explanation structure & clarity
        if (containsAny(lowerAnswer, "first", "second", "specifically", "in contrast", "moreover", "furthermore", "whereas", "on the other hand", "1.", "2.", "•", "-")) {
            score += 2;
            strengths.add("Well-structured and articulated explanation");
        }

        // Clamp score between 1 and 10 for non-empty answers
        score = Math.max(2, Math.min(10, score));

        String feedback;
        if (score >= 9) {
            feedback = "Outstanding response! You demonstrated strong mastery of the core concepts, clear technical terminology, and practical depth.";
        } else if (score >= 7) {
            feedback = "Solid answer! You covered the fundamental points effectively. "
                     + (tips.isEmpty() ? "Consider mentioning performance trade-offs or edge cases to make it perfect." : String.join(". ", tips) + ".");
        } else if (score >= 5) {
            feedback = "Satisfactory answer, but needs more technical accuracy and depth. "
                     + (tips.isEmpty() ? "Ensure you elaborate on definitions and key mechanisms." : String.join(". ", tips) + ".");
        } else {
            feedback = "Basic response. Review the fundamental concepts, include key technical definitions, and provide concrete code examples.";
        }

        return "Score: " + score + "/10\nFeedback: " + feedback;
    }

    private boolean containsAny(String text, String... keywords) {
        if (text == null) return false;
        for (String keyword : keywords) {
            if (text.contains(keyword)) {
                return true;
            }
        }
        return false;
    }
}