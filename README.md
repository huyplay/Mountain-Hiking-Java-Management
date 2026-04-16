# Mountain Hiking Registration System

A Java-based student registration management system for FPT University mountain hiking expeditions. This console application demonstrates solid CRUD operations, comprehensive input validation, data persistence, and Vietnamese-specific business logic (phone carrier validation, campus codes, and tiered discount system).

## Overview

**Mountain Hiking Registration System** is an educational project that helps FPT University students register for organized hiking trips to Vietnamese mountain peaks. Students can create registrations, update their information, search for other participants, and view statistics about mountain peaks and enrollment numbers. The system features validated input, secure data persistence through binary serialization, and a business intelligence component for tracking revenue and participant counts.

### Who It's For
- FPT University students registering for hiking expeditions
- Developers learning Java fundamentals: MVC architecture, CRUD operations, file I/O, validation patterns
- Educational reference for console-based data management systems

### Key Idea
The system combines real-world business logic (Vietnamese phone carriers, FPT campus identification) with software engineering best practices (MVC separation, input validation, data persistence) to create a practical learning environment.

---

## Features

- **New Registration** — Register students with full validation (ID, name, phone, email, mountain selection)
- **Update Registration** — Modify existing student information with optional field updates
- **Display All Registrations** — View all registered students in formatted table output
- **Delete Registration** — Remove student registrations with safety confirmation
- **Search Participants** — Find students by name using case-insensitive keyword matching
- **Filter by Campus** — View registrations filtered by FPT University campus codes (SE, HE, DE, QE, CE)
- **Registration Statistics** — Display enrollment counts and total revenue calculations per mountain peak
- **Save Data** — Persist all registrations to binary file (registrations.dat)
- **Exit Program** — Graceful shutdown with unsaved changes detection
- **Vietnamese Phone Carrier Detection** — Automatic 35% discount for Viettel/VinaPhone students
- **Comprehensive Input Validation** — Student ID format, phone number carrier validation, email format, name constraints

---

## Tech Stack

| Component | Technology |
|-----------|-----------|
| **Programming Language** | Java (JDK 8+) |
| **Build System** | Apache Ant |
| **IDE** | NetBeans |
| **Data Persistence** | Java Object Serialization (ObjectOutputStream) |
| **Data Format** | CSV (mountains reference) + Binary (registrations) |
| **Architecture Pattern** | Model-View-Controller (MVC) |
| **Data Storage Location** | Binary file: `registrations.dat`, CSV: `src/data/MountainList.csv` |

---

## Project Structure

```
MountainHiking/
├── src/
│   ├── model/
│   │   ├── Mountain.java           # Data model for mountain peaks (13 peaks)
│   │   └── Student.java            # Serializable data model for registrations
│   │
│   ├── controller/
│   │   ├── MountainService.java    # Loads mountains from CSV, manages peak data
│   │   ├── StudentService.java     # CRUD operations, file I/O, statistics aggregation
│   │   ├── InputValidator.java     # Comprehensive validation for all user inputs
│   │   └── ReadCSV.java            # CSV parsing utility for mountain data
│   │
│   ├── view/
│   │   └── Main.java               # Interactive console menu interface
│   │
│   └── data/
│       └── MountainList.csv        # 13 Vietnamese mountain peaks (reference data)
│
├── build/
│   └── classes/                    # Compiled .class files
│
├── nbproject/
│   ├── build-impl.xml              # NetBeans build configuration
│   ├── project.properties
│   └── private/
│
├── build.xml                       # Apache Ant build script
├── manifest.mf                     # JAR manifest file
└── registrations.dat               # Binary file (auto-created at runtime)
```

### Component Roles

- **model/** — Domain objects representing core data (mountains and student registrations)
- **controller/** — Business logic: data loading, CRUD operations, validation, statistics
- **view/** — User interface layer: menu-driven console interaction
- **data/** — Static reference data (CSV with 13 Vietnamese mountain peaks)

---

## How It Works

### Application Flow

```
1. APPLICATION START
   ├─ Load mountains from MountainList.csv via MountainService
   ├─ Load previous registrations from registrations.dat (if exists)
   └─ Display main menu

2. USER INTERACTION LOOP
   ├─ Display 9-option menu
   ├─ Accept user input (1-9)
   └─ Route to appropriate operation

3. REGISTRATION OPERATION (Example: New Registration)
   ├─ Prompt for Student ID, Name, Phone, Email, Mountain
   ├─ Validate each input through InputValidator
   │  ├─ Student ID: Check format (XXYYYYYY) and uniqueness
   │  ├─ Phone: Validate Vietnamese carrier and detect for discount
   │  ├─ Email: Check email format
   │  └─ Name: Validate alphabetic + spaces, length 2-20
   ├─ Create Student object with calculated tuition
   ├─ Add to in-memory list (ArrayList<Student>)
   └─ Mark as "unsaved changes"

4. SEARCH/FILTER/STATISTICS
   ├─ Filter StudentService list based on criteria
   ├─ Display formatted results
   └─ Return to menu

5. SAVE OPERATION
   ├─ Serialize studentList to registrations.dat using ObjectOutputStream
   ├─ Clear "unsaved changes" flag
   └─ Confirm success to user

6. EXIT
   ├─ Check for unsaved changes
   ├─ Warn user if data not saved
   └─ Terminate application
```

### Data Flow Diagram

```
MountainList.csv → ReadCSV → MountainService → Mountain[] (13 peaks)
                                                    ↓
Main.java (UI) → StudentService → InputValidator → Student objects
                 ↓                                    ↓
           StudentList (ArrayList)          registrations.dat (Binary)
```

### Discount System Logic

```
Base Tuition: 6,000,000 VND
┌─────────────────────────────────────┐
│ Phone Prefix Check                  │
├─────────────────────────────────────┤
│ Viettel (098, 086, 096, ...)        │ → Apply 35% discount
│ VinaPhone (091, 094, 083, ...)      │ → Apply 35% discount
│ Other Carriers                      │ → Full price
└─────────────────────────────────────┘

Result Examples:
- Viettel: 6,000,000 × 0.65 = 3,900,000 VND (35% off)
- Other:   6,000,000 VND (full price)
```

---

## Getting Started

### Prerequisites

- **Java Development Kit (JDK)** — Version 8 or higher
  - Verify: `java -version`
  - Download: https://www.oracle.com/java/technologies/javase-downloads.html
  
- **Apache Ant** — For building the project
  - Download: https://ant.apache.org/bindownload.cgi
  - Verify: `ant -version`

- **Git** (optional) — To clone the repository

- **NetBeans IDE** (optional) — For development and debugging

### Installation

1. **Clone or download the repository**
   ```bash
   git clone <repository-url>
   cd MountainHiking
   ```

2. **Build the project**
   
   Using Apache Ant:
   ```bash
   ant build
   ```
   
   Or via NetBeans:
   - Open project: File → Open Project → Select MountainHiking folder
   - Build: Right-click project → Build

3. **Verify build**
   
   Check that `build/classes/` contains compiled .class files

### Running the Project

**Option 1: Using Apache Ant**
```bash
ant run
```

**Option 2: Running compiled JAR/class files**
```bash
java -cp build/classes view.Main
```

**Option 3: From NetBeans IDE**
- Right-click project → Run

The application will start with the main menu displaying 9 options.

---

## Usage

### Main Menu Structure

When you run the application, you'll see:

```
========================================
   MOUNTAIN HIKING REGISTRATION SYSTEM
========================================
1. New Registration
2. Update Registration
3. Display Registered List
4. Delete Registration
5. Search Participants
6. Filter by Campus
7. Registration Statistics
8. Save Data
9. Exit Program
========================================
Enter your choice (1-9): _
```

### Operation Details

#### **1. New Registration**
Register a new student for a hiking expedition.

**Input Required:**
- **Student ID** — Format: `XXYYYYYY` (2-char campus code + 6 digits)
  - Example: `SE123456` (SE = Ho Chi Minh City campus)
  - Valid campus codes: SE, HE, DE, QE, CE
  
- **Full Name** — Alphabetic characters and spaces only, 2-20 characters
  - Example: `John Doe`
  
- **Phone Number** — Vietnamese mobile format (10 digits starting with 0)
  - Example: `0987654321`
  - Triggers automatic discount detection
  
- **Email Address** — Standard email format
  - Example: `john@fpt.edu.vn`
  
- **Mountain Selection** — Choose from 13 available peaks (display shown)
  - Example: `1` for Ham Rong

**Validation Checks:**
- Checks for duplicate Student ID and Phone number
- Validates phone prefix against Vietnamese carriers
- Confirms email format
- Verifies mountain selection is valid

**Tuition Calculation:**
- Base: 6,000,000 VND
- If Viettel or VinaPhone detected: 3,900,000 VND (35% discount)
- Otherwise: 6,000,000 VND

**Example Interaction:**
```
Enter Student ID: SE123456
Enter Full Name: Nguyen Van A
Enter Phone: 0988123456
Enter Email: nguyena@student.fpt.edu.vn
Select Mountain:
1. Ham Rong (Lao Cai)
2. Pha Luong (Son La)
[... 13 options ...]
Your choice: 1

✓ Registration successful!
✓ Discount applied: 35% (Viettel detected)
✓ Tuition: 3,900,000 VND
```

#### **2. Update Registration**
Modify an existing student's information.

**Input:** Student ID of existing registration

**Options:** Update Name, Phone, Email, or Mountain (individually, optional fields)

**Example:**
```
Enter Student ID to update: SE123456
Current information displayed...
Update Name? (Y/N): N
Update Phone? (Y/N): Y
New Phone: 0989654321
✓ Updated successfully!
```

#### **3. Display Registered List**
View all registered students in a formatted table.

**Output:**
```
========== REGISTERED STUDENTS ==========
ID       | Name          | Phone        | Mountain
---------|---------------|--------------|----------
SE123456 | Nguyen Van A  | 0988123456   | Ham Rong
HE654321 | Tran Thu B    | 0912345678   | Pha Luong
[...]
Total Registrations: 42
```

#### **4. Delete Registration**
Remove a student's registration permanently.

**Input:** Student ID

**Confirmation:** System asks for confirmation before deletion

**Example:**
```
Enter Student ID to delete: SE123456
Found: Nguyen Van A
Confirm deletion? (Y/N): Y
✓ Deleted successfully!
```

#### **5. Search Participants**
Find students by name keyword (case-insensitive).

**Input:** Name or partial name

**Output:** All matching registrations in table format

**Example:**
```
Enter name to search: Nguyen
========== SEARCH RESULTS ==========
ID       | Name          | Phone        | Mountain
---------|---------------|--------------|----------
SE123456 | Nguyen Van A  | 0988123456   | Ham Rong
HE111111 | Nguyen Thu C  | 0991234567   | Lang Biang
Found: 2 participants
```

#### **6. Filter by Campus**
Show all registrations from a specific FPT University campus.

**Available Campuses:**
- SE — Ho Chi Minh City
- HE — Ha Noi
- DE — Da Nang
- QE — Quy Nhon
- CE — Can Tho

**Example:**
```
Enter campus code (SE/HE/DE/QE/CE): SE
========== STUDENTS FROM SE CAMPUS ==========
ID       | Name          | Phone        | Mountain
---------|---------------|--------------|----------
SE123456 | Nguyen Van A  | 0988123456   | Ham Rong
SE234567 | Tran Minh D   | 0987654321   | Co Tien
Found: 15 participants
```

#### **7. Registration Statistics**
Display enrollment metrics and revenue analysis.

**Output Includes:**
- Participant count per mountain
- Total revenue per mountain
- Overall statistics

**Example:**
```
========== REGISTRATION STATISTICS ==========
Mountain         | Participants | Total Revenue (VND)
-----------------|--------------|--------------------
Ham Rong         | 5            | 27,000,000
Pha Luong        | 8            | 42,000,000
Lang Biang       | 3            | 15,600,000
[...]
Total Participants: 42
Total Revenue: 234,600,000
```

#### **8. Save Data**
Persist all current registrations to `registrations.dat` (binary file).

**Behavior:**
- Serializes all Student objects using ObjectOutputStream
- Creates/overwrites registrations.dat
- Clears "unsaved changes" flag

**Example:**
```
✓ Data saved successfully!
✓ 42 registrations saved to registrations.dat
```

#### **9. Exit Program**
Close the application with safety checks.

**Before Closing:**
- Checks if there are unsaved changes
- Warns user if modifications haven't been saved
- Allows user to return to menu or force exit

**Example:**
```
You have unsaved changes!
Do you want to save before exiting? (Y/N): Y
✓ Data saved successfully!
✓ Goodbye!
```

### Input Validation Examples

#### Student ID Format
```
Valid:   SE123456, HE654321, DE111111, QE999999, CE000000
Invalid: SE12345 (too short), XY123456 (wrong campus)
Error:   Please enter a valid Student ID (XXYYYYYY, campus: SE/HE/DE/QE/CE)
```

#### Phone Number Validation
```
Valid:   0987654321, 0912345678, 0988123456 (any Vietnamese carrier)
Invalid: 987654321 (missing leading 0), 09876543 (too short)
Special: Viettel/VinaPhone prefixes trigger discount detection
Error:   Please enter a valid Vietnamese phone number
```

#### Email Validation
```
Valid:   user@example.com, student@fpt.edu.vn
Invalid: user@.com, @example.com, user(at)example.com
Error:   Please enter a valid email address
```

#### Name Validation
```
Valid:   John Doe, Nguyen Van A, Maria Garcia
Invalid: John123 (numbers), @John (special chars), A (too short)
Length:  2-20 characters
Error:   Please enter name (2-20 chars, alphabetic+spaces only)
```

---

## Data Format

### Mountain Data (CSV)

**File:** `src/data/MountainList.csv`

**Format:**
```
mountainID,mountainName,province,description
1,Ham Rong,Lao Cai,1850m peak
2,Pha Luong,Son La,~2000m - Roof of Moc Chau
3,Lang Biang,Lam Dong,Popular trekking destination
...
```

**Available Mountains (13 peaks):**
1. Ham Rong — Lao Cai (1,850m)
2. Pha Luong — Son La (~2,000m) - "Roof of Moc Chau"
3. Lang Biang — Lam Dong
4. Co Tien — Khanh Hoa (Peak 1)
5. Co Tien — Khanh Hoa (Peak 2)
6. Co Tien — Khanh Hoa (Peak 3)
7-13. Additional Vietnamese mountain peaks

### Student Registration Data (Binary)

**File:** `registrations.dat` (auto-created at runtime)

**Format:** Binary serialization of Java objects

**Student Object Structure:**
```
├── studentID: String         (e.g., "SE123456")
├── name: String              (e.g., "Nguyen Van A")
├── phone: String             (e.g., "0988123456")
├── email: String             (e.g., "nguyena@student.fpt.edu.vn")
├── mountainCode: String      (e.g., "MT01")
└── tuition: long             (e.g., 3900000 or 6000000 VND)
```

**Mountain Object Structure:**
```
├── mountainID: int           (1-13)
├── mountainCode: String      (auto-generated: "MT" + ID)
├── mountainName: String      (e.g., "Ham Rong")
├── province: String          (e.g., "Lao Cai")
└── description: String       (e.g., "1850m peak")
```

---

## Validation Rules

### Student ID Validation

**Pattern:** `XXYYYYYY`
- First 2 characters: Campus code (SE, HE, DE, QE, CE)
- Next 6 characters: Numeric digits (0-9)
- Length: Exactly 8 characters
- Uniqueness: Must not already exist in registrations

**Implementation Location:** `InputValidator.java`

**Campus Code Meanings:**
| Code | Campus | Location |
|------|--------|----------|
| SE | Software Engineering | Ho Chi Minh City |
| HE | HE Foundation | Ha Noi |
| DE | Design Engineering | Da Nang |
| QE | Quality Engineering | Quy Nhon |
| CE | Cloud Engineering | Can Tho |

### Phone Number Validation

**Pattern:** Vietnamese mobile format
- Prefix: 0 (required)
- Length: 10 digits total
- Valid prefixes: Recognized Vietnamese carriers

**Carrier Detection (Discount Trigger):**
```
Viettel:   098, 086, 096, 097, 0162, 0163
VinaPhone: 091, 094, 083, 0164, 0165
MobiFone:  090, 093, 089, 0120, 0121
Vietnamobile: 092, 088, 0122
Gmobile:   099, 0123
iTel:      087, 0124
Wintel:    085, 0125
```

**Discount Application:**
- If Viettel or VinaPhone: **35% discount** applied
- Other carriers: Full price

### Email Validation

**Pattern:** Standard email regex
- Format: `username@domain.extension`
- Requirements: Valid characters, @ symbol, domain name
- Examples: `user@fpt.edu.vn`, `student@example.com`

### Name Validation

**Constraints:**
- Length: 2-20 characters
- Characters: Alphabetic (a-z, A-Z) and spaces only
- No numbers, punctuation, or special characters
- At least one valid character

### Mountain Selection Validation

**Constraints:**
- Selection must be 1-13
- Corresponds to loaded mountains from CSV
- Validates against actual mountain list size
- Error if selection out of range

### Uniqueness Validation

**Duplicate Prevention:**
- **Student ID:** No two registrations can have same ID
- **Phone Number:** No two registrations can have same phone
- Check performed during New Registration
- Error message if duplicate detected

---

## Architecture Notes

### Model-View-Controller (MVC) Pattern

**Model Layer** (`src/model/`)
- `Mountain.java` — Immutable data class representing mountain peaks
- `Student.java` — Serializable data class representing registrations
- Responsibility: Pure data representation, no business logic

**View Layer** (`src/view/`)
- `Main.java` — Console UI, user interaction, menu display
- Responsibility: Display, user input, formatted output
- No direct data manipulation

**Controller Layer** (`src/controller/`)
- `MountainService.java` — Manages mountain data, CSV loading
- `StudentService.java` — CRUD operations, file I/O, business logic
- `InputValidator.java` — Validates all user inputs
- `ReadCSV.java` — CSV parsing utility
- Responsibility: Business logic, data flow, validation coordination

### Data Flow

```
User Input (Main)
    ↓
InputValidator (validates format & uniqueness)
    ↓
StudentService (applies business logic: discounts, CRUD)
    ↓
Student/Mountain Models (storage in memory)
    ↓
ObjectOutputStream (persistence to binary file)
```

### Key Design Patterns Used

1. **MVC Separation** — Clear boundaries between layers
2. **CRUD Pattern** — Create, Read, Update, Delete operations
3. **Validation Pattern** — Centralized validation in InputValidator
4. **Service Layer** — Business logic isolated in Service classes
5. **Utility Pattern** — CSV reading as reusable utility

### Exception Handling

- File I/O exceptions caught and reported
- Input validation prevents invalid data
- Null checks prevent NullPointerException
- CSV loading handles malformed data gracefully

---

## Future Improvements

### Short-term Enhancements
1. **GUI Interface** — Replace console with Swing/JavaFX for better UX
2. **Database Backend** — Migrate from binary files to MySQL/PostgreSQL
3. **Enhanced Search** — Search by multiple criteria (mountain, campus, tuition range)
4. **Data Export** — Generate PDF/Excel reports

### Medium-term Features
5. **RESTful API** — Expose functionality as HTTP endpoints for web integration
6. **Email Integration** — Send confirmation emails upon registration
7. **Payment Gateway** — Integrate payment processing for tuition collection
8. **Role-based Access Control** — Admin vs. student user roles with different permissions

### Long-term Roadmap
9. **Mobile App** — Native iOS/Android app for on-the-go registration
10. **Machine Learning** — Predict popular mountains, optimize pricing
11. **Real-time Notifications** — Push notifications for trip updates
12. **Community Features** — Social aspects (user profiles, hiking forums)
13. **Analytics Dashboard** — Advanced reporting and business intelligence
14. **Internationalization** — Multi-language support (Vietnamese, English, etc.)

---

## Notes

### Project Assumptions

- **Educational Context** — Designed as a learning project for Java fundamentals
- **Tuition in VND** — All prices hardcoded in Vietnamese Dong (6,000,000 VND base)
- **FPT University Context** — Campus codes and discount logic specific to FPT
- **Single-threaded Execution** — Console application runs sequentially
- **Local File Storage** — No network/cloud integration
- **In-memory Operations** — Registrations loaded into ArrayList at startup

### Limitations

- **Console-only Interface** — No graphical user interface
- **Single User Session** — No multi-user concurrent access support
- **Manual Data Management** — No automatic backup or version history
- **Limited Error Recovery** — Basic error handling, no recovery mechanisms
- **Static Mountain List** — Mountains loaded once at startup, not dynamically updatable
- **No Advanced Filtering** — Filter only by campus code (not combined criteria)
- **Basic Statistics** — Simple aggregation only (count, revenue per mountain)

### Data Persistence Considerations

- **Binary Format** — Uses Java serialization (`.dat` file), platform-dependent
- **No Data Encryption** — Registrations stored in plain binary format
- **Unsaved Changes Detection** — In-memory flag warns user before exit
- **No Automatic Backups** — Manual save required, no automatic backup mechanism
- **File Locking** — Not handled; assumes single application instance

### Performance Notes

- **Small Dataset** — Optimized for <1000 registrations (ArrayList operations sufficient)
- **O(n) Search** — Linear search for name and campus filtering
- **Statistics Aggregation** — HashMap-based grouping for efficient O(n) calculation
- **No Indexing** — Direct array iteration; would benefit from database indexes at scale

### Vietnamese-specific Implementation

- **Phone Carrier Detection** — Hardcoded carrier prefixes for Vietnam mobile operators
- **Discount Logic** — Business rule: Viettel/VinaPhone get 35% discount
- **Campus Identification** — FPT University campus codes embedded in Student ID
- **Currency** — All monetary values in Vietnamese Dong (VND)
- **Peak Selection** — 13 specific Vietnamese mountains in reference data

---

## License

[Specify license if applicable, e.g., MIT, Apache 2.0, GPL, or Educational Use Only]

---

## Contact & Support

For questions, issues, or contributions:
- [Add GitHub issues link]
- [Add contact email if applicable]
- [Add contributor guidelines if applicable]

---

## Changelog

**Version 1.0.0** — Initial release
- Basic CRUD operations for student registrations
- Mountain peak data from CSV
- Binary file persistence
- Comprehensive input validation
- Statistics aggregation
- Filter by campus functionality

