DROP
DATABASE IF EXISTS byebyechallan;
    CREATE
DATABASE IF NOT EXISTS byebyechallan;
    USE
byebyechallan;


-- 2024-06-17: Added core_state_country_m table to store state and country information for documents.
CREATE TABLE core_country_state_m
(
    country_state_id VARCHAR(64) PRIMARY KEY NOT NULL,
    country_id       VARCHAR(64)             NOT NULL,
    state_id         VARCHAR(64)             NOT NULL,
    country_name     VARCHAR(256)            NOT NULL,
    state_name       VARCHAR(256)            NOT NULL,
    is_deleted       TINYINT   DEFAULT False,
    created_by       BIGINT,
    created_time     TIMESTAMP DEFAULT NOW()
);

-- 2024-06-17: Inserted state and country information for India into core_country_state_m table.
INSERT INTO core_country_state_m
(state_name, country_state_id, country_id, country_name, state_id, is_deleted, created_by,
 created_time)
VALUES ("Andhra Pradesh", "IN-AP", "IN", "India", "AP", false, 0, NOW()),
       ("Arunachal Pradesh", "IN-AR", "IN", "India", "AR", false, 0, NOW()),
       ("Assam", "IN-AS", "IN", "India", "AS", false, 0, NOW()),
       ("Bihar", "IN-BR", "IN", "India", "BR", false, 0, NOW()),
       ("Chhattisgarh", "IN-CG", "IN", "India", "CG", false, 0, NOW()),
       ("Goa", "IN-GA", "IN", "India", "GA", false, 0, NOW()),
       ("Gujarat", "IN-GJ", "IN", "India", "GJ", false, 0, NOW()),
       ("Haryana", "IN-HR", "IN", "India", "HR", false, 0, NOW()),
       ("Himachal Pradesh", "IN-HP", "IN", "India", "HP", false, 0, NOW()),
       ("Jharkhand", "IN-JH", "IN", "India", "JH", false, 0, NOW()),
       ("Karnataka", "IN-KA", "IN", "India", "KA", false, 0, NOW()),
       ("Kerala", "IN-KL", "IN", "India", "KL", false, 0, NOW()),
       ("Madhya Pradesh", "IN-MP", "IN", "India", "MP", false, 0, NOW()),
       ("Maharashtra", "IN-MH", "IN", "India", "MH", false, 0, NOW()),
       ("Manipur", "IN-MN", "IN", "India", "MN", false, 0, NOW()),
       ("Meghalaya", "IN-ML", "IN", "India", "ML", false, 0, NOW()),
       ("Mizoram", "IN-MZ", "IN", "India", "MZ", false, 0, NOW()),
       ("Nagaland", "IN-NL", "IN", "INDIA", "NL", false, 0, NOW()),
       ("Odisha", "IN-OD", "IN", "INDIA", "OD", false, 0, NOW()),
       ("Punjab", "IN-PB", "IN", "INDIA", "PB", false, 0, NOW()),
       ("Rajasthan", "IN-RJ", "IN", "India", "RJ", false, 0, NOW()),
       ("Sikkim", "IN-SK", "IN", "India", "SK", false, 0, NOW()),
       ("Tamil Nadu", "IN-TN", "IN", "India", "TN", false, 0, NOW()),
       ("Telangana", "IN-TS", "IN", "India", "TS", false, 0, NOW()),
       ("Tripura", "IN-TR", "IN", "India", "TR", false, 0, NOW()),
       ("Uttar Pradesh", "IN-UP", "IN", "India", "UP", false, 0, NOW()),
       ("Uttarakhand", "IN-UK", "IN", "India", "UK", false, 0, NOW()),
       ("West Bengal", "IN-WB", "IN", "India", "WB", false, 0, NOW()),
       ("Andaman and Nicobar Islands", "IN-AN", "IN", "INDIA", "AN", false, 0, NOW()),
       ("Chandigarh", "IN-CH", "IN", "INDIA", "CH", false, 0, NOW()),
       ("Dadra and Nagar Haveli and Daman and Diu", "IN-DH", "IN", "INDIA", "DH", false, 0, NOW()),
       ("Delhi", "IN-DL", "IN", "INDIA", "DL", false, 0, NOW()),
       ("Jammu and Kashmir", "IN-JK", "IN", "INDIA", "JK", false, 0, NOW()),
       ("Ladakh", "IN-LA", "IN", "India", "LA", false, 0, NOW()),
       ("Lakshadweep", "IN-LD", "IN", "India", "LD", false, 0, NOW()),
       ("Puducherry", "IN-PY", "IN", "India", "PY", false, 0, NOW());


CREATE TABLE core_vehicle_category_m
(
    vehicle_category_id   VARCHAR(64) PRIMARY KEY,
    vehicle_category_name VARCHAR(256) NOT NULL,
    is_deleted            TINYINT   DEFAULT 0,
    created_by            BIGINT    DEFAULT 0,
    created_time          TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO core_vehicle_category_m (vehicle_category_id, vehicle_category_name)
VALUES ('PRIVATE_VEHICLE', 'Private Vehicle'),
       ('COMMERCIAL_PASSENGER', 'Commercial Passenger Vehicle'),
       ('GOODS_VEHICLE', 'Goods Vehicle'),
       ('SPECIAL_PURPOSE', 'Special Purpose Vehicle'),
       ('AGRICULTURAL', 'Agricultural Vehicle');

CREATE TABLE core_vehicle_type_m
(
    vehicle_type_id     VARCHAR(64) PRIMARY KEY,
    vehicle_type_name   VARCHAR(256) NOT NULL,
    vehicle_category_id VARCHAR(64),
    is_deleted          TINYINT   DEFAULT 0,
    created_by          BIGINT    DEFAULT 0,
    created_time        TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO core_vehicle_type_m (vehicle_type_id, vehicle_type_name, vehicle_category_id)
VALUES ('BIKE', 'Bike', 'PRIVATE_VEHICLE'),
       ('SCOOTER', 'Scooter', 'PRIVATE_VEHICLE'),
       ('CAR', 'Car', 'PRIVATE_VEHICLE'),
       ('SUV', 'SUV', 'PRIVATE_VEHICLE'),
       ('HATCHBACK', 'Hatchback', 'PRIVATE_VEHICLE'),
       ('SEDAN', 'Sedan', 'PRIVATE_VEHICLE'),

       ('AUTO_RICKSHAW', 'Auto Rickshaw', 'COMMERCIAL_PASSENGER'),
       ('BUS', 'Bus', 'COMMERCIAL_PASSENGER'),

       ('PICKUP_TRUCK', 'Pickup Truck', 'GOODS_VEHICLE'),
       ('TRUCK', 'Truck', 'GOODS_VEHICLE'),

       ('TRACTOR', 'Tractor', 'AGRICULTURAL'),

       ('AMBULANCE', 'Ambulance', 'SPECIAL_PURPOSE');


-- Country Data
CREATE TABLE core_countries_m
(
    country_id   VARCHAR(10) PRIMARY KEY,
    country_name VARCHAR(100) NOT NULL,
    phone_code   VARCHAR(10)  NOT NULL
);

INSERT INTO core_countries_m (country_id, country_name, phone_code)
VALUES ('AF', 'Afghanistan', '+93'),
       ('AL', 'Albania', '+355'),
       ('DZ', 'Algeria', '+213'),
       ('AD', 'Andorra', '+376'),
       ('AO', 'Angola', '+244'),
       ('AR', 'Argentina', '+54'),
       ('AM', 'Armenia', '+374'),
       ('AU', 'Australia', '+61'),
       ('AT', 'Austria', '+43'),
       ('AZ', 'Azerbaijan', '+994'),
       ('BS', 'Bahamas', '+1-242'),
       ('BH', 'Bahrain', '+973'),
       ('BD', 'Bangladesh', '+880'),
       ('BB', 'Barbados', '+1-246'),
       ('BY', 'Belarus', '+375'),
       ('BE', 'Belgium', '+32'),
       ('BZ', 'Belize', '+501'),
       ('BJ', 'Benin', '+229'),
       ('BT', 'Bhutan', '+975'),
       ('BO', 'Bolivia', '+591'),
       ('BA', 'Bosnia and Herzegovina', '+387'),
       ('BW', 'Botswana', '+267'),
       ('BR', 'Brazil', '+55'),
       ('BN', 'Brunei', '+673'),
       ('BG', 'Bulgaria', '+359'),
       ('BF', 'Burkina Faso', '+226'),
       ('BI', 'Burundi', '+257'),
       ('CV', 'Cape Verde', '+238'),
       ('KH', 'Cambodia', '+855'),
       ('CM', 'Cameroon', '+237'),
       ('CA', 'Canada', '+1'),
       ('CF', 'Central African Republic', '+236'),
       ('TD', 'Chad', '+235'),
       ('CL', 'Chile', '+56'),
       ('CN', 'China', '+86'),
       ('CO', 'Colombia', '+57'),
       ('KM', 'Comoros', '+269'),
       ('CG', 'Congo', '+242'),
       ('CD', 'Democratic Republic of the Congo', '+243'),
       ('CR', 'Costa Rica', '+506'),
       ('HR', 'Croatia', '+385'),
       ('CU', 'Cuba', '+53'),
       ('CY', 'Cyprus', '+357'),
       ('CZ', 'Czech Republic', '+420'),
       ('DK', 'Denmark', '+45'),
       ('DJ', 'Djibouti', '+253'),
       ('DM', 'Dominica', '+1-767'),
       ('DO', 'Dominican Republic', '+1-809'),
       ('EC', 'Ecuador', '+593'),
       ('EG', 'Egypt', '+20'),
       ('SV', 'El Salvador', '+503'),
       ('GQ', 'Equatorial Guinea', '+240'),
       ('ER', 'Eritrea', '+291'),
       ('EE', 'Estonia', '+372'),
       ('SZ', 'Eswatini', '+268'),
       ('ET', 'Ethiopia', '+251'),
       ('FJ', 'Fiji', '+679'),
       ('FI', 'Finland', '+358'),
       ('FR', 'France', '+33'),
       ('GA', 'Gabon', '+241'),
       ('GM', 'Gambia', '+220'),
       ('GE', 'Georgia', '+995'),
       ('DE', 'Germany', '+49'),
       ('GH', 'Ghana', '+233'),
       ('GR', 'Greece', '+30'),
       ('GD', 'Grenada', '+1-473'),
       ('GT', 'Guatemala', '+502'),
       ('GN', 'Guinea', '+224'),
       ('GW', 'Guinea-Bissau', '+245'),
       ('GY', 'Guyana', '+592'),
       ('HT', 'Haiti', '+509'),
       ('HN', 'Honduras', '+504'),
       ('HU', 'Hungary', '+36'),
       ('IS', 'Iceland', '+354'),
       ('IN', 'India', '+91'),
       ('ID', 'Indonesia', '+62'),
       ('IR', 'Iran', '+98'),
       ('IQ', 'Iraq', '+964'),
       ('IE', 'Ireland', '+353'),
       ('IL', 'Israel', '+972'),
       ('IT', 'Italy', '+39'),
       ('JM', 'Jamaica', '+1-876'),
       ('JP', 'Japan', '+81'),
       ('JO', 'Jordan', '+962'),
       ('KZ', 'Kazakhstan', '+7'),
       ('KE', 'Kenya', '+254'),
       ('KI', 'Kiribati', '+686'),
       ('KP', 'North Korea', '+850'),
       ('KR', 'South Korea', '+82'),
       ('KW', 'Kuwait', '+965'),
       ('KG', 'Kyrgyzstan', '+996'),
       ('LA', 'Laos', '+856'),
       ('LV', 'Latvia', '+371'),
       ('LB', 'Lebanon', '+961'),
       ('LS', 'Lesotho', '+266'),
       ('LR', 'Liberia', '+231'),
       ('LY', 'Libya', '+218'),
       ('LI', 'Liechtenstein', '+423'),
       ('LT', 'Lithuania', '+370'),
       ('LU', 'Luxembourg', '+352'),
       ('MG', 'Madagascar', '+261'),
       ('MW', 'Malawi', '+265'),
       ('MY', 'Malaysia', '+60'),
       ('MV', 'Maldives', '+960'),
       ('ML', 'Mali', '+223'),
       ('MT', 'Malta', '+356'),
       ('MH', 'Marshall Islands', '+692'),
       ('MR', 'Mauritania', '+222'),
       ('MU', 'Mauritius', '+230'),
       ('MX', 'Mexico', '+52'),
       ('FM', 'Micronesia', '+691'),
       ('MD', 'Moldova', '+373'),
       ('MC', 'Monaco', '+377'),
       ('MN', 'Mongolia', '+976'),
       ('ME', 'Montenegro', '+382'),
       ('MA', 'Morocco', '+212'),
       ('MZ', 'Mozambique', '+258'),
       ('MM', 'Myanmar', '+95'),
       ('NA', 'Namibia', '+264'),
       ('NR', 'Nauru', '+674'),
       ('NP', 'Nepal', '+977'),
       ('NL', 'Netherlands', '+31'),
       ('NZ', 'New Zealand', '+64'),
       ('NI', 'Nicaragua', '+505'),
       ('NE', 'Niger', '+227'),
       ('NG', 'Nigeria', '+234'),
       ('NO', 'Norway', '+47'),
       ('OM', 'Oman', '+968'),
       ('PK', 'Pakistan', '+92'),
       ('PW', 'Palau', '+680'),
       ('PA', 'Panama', '+507'),
       ('PG', 'Papua New Guinea', '+675'),
       ('PY', 'Paraguay', '+595'),
       ('PE', 'Peru', '+51'),
       ('PH', 'Philippines', '+63'),
       ('PL', 'Poland', '+48'),
       ('PT', 'Portugal', '+351'),
       ('QA', 'Qatar', '+974'),
       ('RO', 'Romania', '+40'),
       ('RU', 'Russia', '+7'),
       ('RW', 'Rwanda', '+250'),
       ('KN', 'Saint Kitts and Nevis', '+1-869'),
       ('LC', 'Saint Lucia', '+1-758'),
       ('VC', 'Saint Vincent and the Grenadines', '+1-784'),
       ('WS', 'Samoa', '+685'),
       ('SM', 'San Marino', '+378'),
       ('ST', 'Sao Tome and Principe', '+239'),
       ('SA', 'Saudi Arabia', '+966'),
       ('SN', 'Senegal', '+221'),
       ('RS', 'Serbia', '+381'),
       ('SC', 'Seychelles', '+248'),
       ('SL', 'Sierra Leone', '+232'),
       ('SG', 'Singapore', '+65'),
       ('SK', 'Slovakia', '+421'),
       ('SI', 'Slovenia', '+386'),
       ('SB', 'Solomon Islands', '+677'),
       ('SO', 'Somalia', '+252'),
       ('ZA', 'South Africa', '+27'),
       ('ES', 'Spain', '+34'),
       ('LK', 'Sri Lanka', '+94'),
       ('SD', 'Sudan', '+249'),
       ('SR', 'Suriname', '+597'),
       ('SE', 'Sweden', '+46'),
       ('CH', 'Switzerland', '+41'),
       ('SY', 'Syria', '+963'),
       ('TW', 'Taiwan', '+886'),
       ('TJ', 'Tajikistan', '+992'),
       ('TZ', 'Tanzania', '+255'),
       ('TH', 'Thailand', '+66'),
       ('TL', 'Timor-Leste', '+670'),
       ('TG', 'Togo', '+228'),
       ('TO', 'Tonga', '+676'),
       ('TT', 'Trinidad and Tobago', '+1-868'),
       ('TN', 'Tunisia', '+216'),
       ('TR', 'Turkey', '+90'),
       ('TM', 'Turkmenistan', '+993'),
       ('TV', 'Tuvalu', '+688'),
       ('UG', 'Uganda', '+256'),
       ('UA', 'Ukraine', '+380'),
       ('AE', 'United Arab Emirates', '+971'),
       ('GB', 'United Kingdom', '+44'),
       ('US', 'United States', '+1'),
       ('UY', 'Uruguay', '+598'),
       ('UZ', 'Uzbekistan', '+998'),
       ('VU', 'Vanuatu', '+678'),
       ('VA', 'Vatican City', '+379'),
       ('VE', 'Venezuela', '+58'),
       ('VN', 'Vietnam', '+84'),
       ('YE', 'Yemen', '+967'),
       ('ZM', 'Zambia', '+260'),
       ('ZW', 'Zimbabwe', '+263');


-- Registration Types
CREATE TABLE core_registration_type_m
(
    registration_code VARCHAR(32) PRIMARY KEY,
    country_id        VARCHAR(15) NOT NULL,
    registration_type VARCHAR(50) NOT NULL,
    is_deleted        TINYINT     NOT NULL DEFAULT 0
);

INSERT INTO core_registration_type_m (registration_code, registration_type, country_id)
VALUES ('IN_STATE', 'India State Registration', 'IN'),
       ('IN_BH', 'Bharat Series Registration', 'IN'),
       ('US_STATE', 'US State Registration', 'US'),
       ('GB_STANDARD', 'UK Standard Registration', 'GB'),
       ('DE_STANDARD', 'Germany Standard Registration', 'DE'),
       ('AE_STANDARD', 'UAE Standard Registration', 'AE');


CREATE TABLE core_user_m
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    name         VARCHAR(256)        NOT NULL,
    email        VARCHAR(256) UNIQUE NOT NULL,
    mobile       VARCHAR(128) UNIQUE NOT NULL,
    password     VARCHAR(255)        NOT NULL,
    role         VARCHAR(50)         NOT NULL,
    is_deleted   TINYINT   DEFAULT 0,
    created_time TIMESTAMP DEFAULT NOW(),
    updated_time TIMESTAMP DEFAULT NOW()
) AUTO_INCREMENT = 1001;



CREATE TABLE user_profile_t
(
    id           INT PRIMARY KEY AUTO_INCREMENT,
    user_id      LONG         NOT NULL,
    name         VARCHAR(256) NOT NULL,
    is_deleted   TINYINT   DEFAULT 0,
    created_time TIMESTAMP DEFAULT NOW()
);

CREATE TABLE user_document_t
(
    id                      INT PRIMARY KEY AUTO_INCREMENT,
    profile_id              LONG         NOT NULL,
    vehicle_registration_no VARCHAR(128) NOT NULL,
    doc_template_id         VARCHAR(255) NOT NULL,
    doc_name                VARCHAR(255) NOT NULL,
    doc_id                  VARCHAR(128) NOT NULL,
    doc_s3_upload           VARCHAR(255) NOT NULL,
    uploaded_date           TIMESTAMP DEFAULT NOW(),
    expiry_date             TIMESTAMP DEFAULT NOW(),
    is_sms                  TINYINT   DEFAULT 1,
    is_email                TINYINT   DEFAULT 1,
    is_whatsapp             TINYINT   DEFAULT 1,
    notification_time       TIMESTAMP    NOT NULL,
    is_deleted              TINYINT   DEFAULT 0,
    created_time            TIMESTAMP DEFAULT NOW(),
    updated_time            TIMESTAMP DEFAULT NOW()
);

-- Refresh Token Table to store refresh tokens for users
CREATE TABLE core_refresh_token_tr
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    user_id     INT          NOT NULL,
    token       VARCHAR(256) NOT NULL UNIQUE,
    expiry_time TIMESTAMP    NOT NULL
);


DELETE
FROM core_country_state_m
WHERE (country_state_id = 'IN-DH');
INSERT INTO core_country_state_m
VALUES ('IN-DN', 'IN', 'DN', 'INDIA', 'Dadra and Nagar Haveli', 0, 0, NOW()),
       ('IN-DD', 'IN', 'DN', 'INDIA', 'Daman and Diu', 0, 0, NOW());


CREATE TABLE core_profile_vehicle_tr
(
    id                      BIGINT PRIMARY KEY AUTO_INCREMENT,
    profile_id              BIGINT       NOT NULL,
    vehicle_name            VARCHAR(128) NOT NULL,
    vehicle_registration_no VARCHAR(129) NOT NULL,
    is_deleted              TINYINT      NOT NULL DEFAULT 0,
    created_time            TIMESTAMP             DEFAULT NOW(),
    updated_time            TIMESTAMP             DEFAULT NOW()
);