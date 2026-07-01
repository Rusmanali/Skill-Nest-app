package com.example.skillnest;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Arrays;
import java.util.List;

public class FirebaseDataPopulator {

    public static void populateQuestions() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("quizzes");

        // Mobile Development
        addCourseQuestions(ref, "Mobile Development", Arrays.asList(
            new Question("What is Android?", Arrays.asList("A web browser", "An operating system", "A language", "A database"), 1),
            new Question("Primary language for Android?", Arrays.asList("Swift", "Kotlin", "Python", "Ruby"), 1),
            new Question("What does SDK stand for?", Arrays.asList("Software Dev Kit", "Standard Dev Kit", "System Dev Kit", "Software Design Kit"), 0),
            new Question("UI layout file format?", Arrays.asList("XML", "JSON", "HTML", "CSS"), 0),
            new Question("What is an Activity?", Arrays.asList("Background service", "A single screen", "Data storage", "Network request"), 1),
            new Question("Navigation tool?", Arrays.asList("Link", "Intent", "Map", "Fragment"), 1),
            new Question("Configuration file?", Arrays.asList("main.xml", "AndroidManifest.xml", "strings.xml", "config.xml"), 1),
            new Question("Data passing tool?", Arrays.asList("Bundle", "Database", "Layout", "Network"), 0),
            new Question("What is an Intent?", Arrays.asList("Task", "Action request", "Style", "Debug"), 1),
            new Question("What is Gradle?", Arrays.asList("UI Tool", "Build system", "Language", "Database"), 1)
        ));

        // Web Development
        addCourseQuestions(ref, "Web Development", Arrays.asList(
            new Question("HTML stands for?", Arrays.asList("Hyper Text Markup Language", "Hyper Tool Markup Language", "High Text Markup Language", "Hyper Text Main Language"), 0),
            new Question("Which tag is used for links?", Arrays.asList("<link>", "<a>", "<href>", "<url>"), 1),
            new Question("CSS stands for?", Arrays.asList("Colorful Style Sheets", "Cascading Style Sheets", "Creative Style Sheets", "Computer Style Sheets"), 1),
            new Question("Which is not a JS framework?", Arrays.asList("React", "Angular", "Vue", "Django"), 3),
            new Question("HTTP stands for?", Arrays.asList("HyperText Transfer Protocol", "HyperText Type Protocol", "HighText Transfer Protocol", "HyperText Transmit Protocol"), 0),
            new Question("What is the use of <div>?", Arrays.asList("Link", "Sectioning", "Image", "Button"), 1),
            new Question("JavaScript is used for?", Arrays.asList("Styling", "Structure", "Interactivity", "Database"), 2),
            new Question("Which property changes text color?", Arrays.asList("text-color", "color", "font-color", "background"), 1),
            new Question("What is a Responsive design?", Arrays.asList("Fast loading", "Mobile friendly", "Colorful", "Secure"), 1),
            new Question("SQL is used for?", Arrays.asList("Styling", "Scripting", "Databases", "Networking"), 2)
        ));

        // UI/UX Design
        addCourseQuestions(ref, "UI/UX Design", Arrays.asList(
            new Question("UI stands for?", Arrays.asList("User Interest", "User Interface", "Universal Interface", "User Interaction"), 1),
            new Question("UX stands for?", Arrays.asList("User Example", "User Experience", "User Extra", "Universal Experience"), 1),
            new Question("Primary tool for UI design?", Arrays.asList("Excel", "Figma", "Word", "PowerPoint"), 1),
            new Question("What is a Wireframe?", Arrays.asList("Final product", "Blueprint", "Color palette", "Logo"), 1),
            new Question("Which is a primary color?", Arrays.asList("Orange", "Green", "Red", "Purple"), 2),
            new Question("What is Typography?", Arrays.asList("Map making", "Arranging type", "Photo editing", "Logo design"), 1),
            new Question("White space is?", Arrays.asList("Wasted space", "Empty space", "Error", "Background color"), 1),
            new Question("Accessibility means?", Arrays.asList("Fast access", "Use by everyone", "Low price", "Internet speed"), 1),
            new Question("Color psychology studies?", Arrays.asList("Paint mixing", "Emotions from colors", "Color blindness", "Web safe colors"), 1),
            new Question("A Persona is?", Arrays.asList("Real user", "User archetype", "Competitor", "Developer"), 1)
        ));

        // AI & ML
        addCourseQuestions(ref, "AI & ML", Arrays.asList(
            new Question("AI stands for?", Arrays.asList("Auto Intelligence", "Artificial Intelligence", "Advanced Info", "Actual Intel"), 1),
            new Question("ML stands for?", Arrays.asList("Main Learning", "Machine Learning", "Mobile Learning", "Manual Learning"), 1),
            new Question("Primary language for ML?", Arrays.asList("Java", "Python", "C++", "PHP"), 1),
            new Question("What is a Neural Network?", Arrays.asList("Internet connection", "Computing system", "Brain surgery", "Social media"), 1),
            new Question("Supervised learning needs?", Arrays.asList("Unlabeled data", "Labeled data", "No data", "Fast internet"), 1),
            new Question("What is Big Data?", Arrays.asList("Large datasets", "Big computers", "Large software", "Big companies"), 0),
            new Question("What is Deep Learning?", Arrays.asList("Subfield of ML", "Underwater search", "Reading books", "Learning fast"), 0),
            new Question("What is an Algorithm?", Arrays.asList("Mathematical rule", "Language", "Computer", "Website"), 0),
            new Question("NLP stands for?", Arrays.asList("Natural Logic Process", "Natural Language Processing", "New Language Process", "Node List Process"), 1),
            new Question("A Chatbot uses?", Arrays.asList("Graphics", "AI/NLP", "Excel", "Only Buttons"), 1)
        ));
    }

    public static void populateLessons() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("lessons");

        // Mobile Development Lessons
        addLesson(ref, "Mobile Development", "Introduction to Android Development", "Lesson 1", 
            "Android is an operating system developed by Google for smartphones, tablets, TVs, and many other devices.",
            "✓ What Android is\n✓ Android Architecture\n✓ Android Studio\n✓ How Android Apps Work", "25%");
        
        addLesson(ref, "Mobile Development", "Activities and App Navigation", "Lesson 2",
            "Activities are the building blocks of an Android application. Users navigate between different activities to perform tasks.",
            "✓ Activity Lifecycle\n✓ Intent and Explicit Navigation\n✓ Passing Data Between Activities", "50%");

        addLesson(ref, "Mobile Development", "User Interface Design", "Lesson 3",
            "UI Design is the process of creating attractive, user-friendly, and responsive screens for Android applications.",
            "✓ Android Layouts\n✓ Material Design\n✓ Best Practices for UI", "75%");

        addLesson(ref, "Mobile Development", "RecyclerView and Lists", "Lesson 4",
            "RecyclerView reuses item views, making apps faster and more memory-efficient when displaying large lists.",
            "✓ Setting Up a RecyclerView\n✓ Adapters and ViewHolders\n✓ Handling Click Events", "100%");

        // Web Development Lessons
        addLesson(ref, "Web Development", "Introduction to Web Development", "Lesson 1",
            "Web Development is the process of building and maintaining websites and web applications.",
            "✓ Frontend vs Backend\n✓ Tools Required\n✓ Static vs Dynamic", "25%");

        addLesson(ref, "Web Development", "HTML Fundamentals", "Lesson 2",
            "HTML is the standard language used to create the structure of web pages.",
            "✓ HTML Structure\n✓ Lists and Tables\n✓ Building Forms", "50%");

        // UI/UX Design Lessons
        addLesson(ref, "UI/UX Design", "Introduction to UI/UX Design", "Lesson 1",
            "UI focuses on the look and feel, while UX focuses on how users interact with a product.",
            "✓ UI vs UX\n✓ User-Centered Design\n✓ Design Principles", "25%");

        // AI & ML Lessons
        addLesson(ref, "AI & ML", "Introduction to AI and Machine Learning", "Lesson 1",
            "AI enables computers to perform human-like tasks, while ML allows systems to learn from data.",
            "✓ AI vs ML\n✓ Types of Machine Learning\n✓ Real-World Applications", "50%");
    }

    private static void addLesson(DatabaseReference ref, String category, String title, String number, String overview, String points, String progress) {
        Lesson lesson = new Lesson(category, title, number, overview, points, progress);
        ref.child(category.replace(" ", "_")).child(title.replace(" ", "_")).setValue(lesson);
    }

    private static void addCourseQuestions(DatabaseReference ref, String courseName, List<Question> questions) {
        ref.child(courseName).setValue(questions);
    }
}