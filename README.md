# POC GENERATE DOCUMENT

This project provides a service for generating documents from templates and converting them from DOCX to PDF format. It is designed to be used as part of a web application where users can request documents by providing necessary data.

## FLOWCHART
![image](https://github.com/TongTunyarat/poc-generate-document/assets/113578736/72db42b0-17e4-420f-b8c7-df934b1aa38d)

| Step | Description |
|------|-------------|
| A    | Start |
| B    | User sends request with JSON data |
| C    | Fetch template from repository |
| C.1  | Template exists: Load template file from path |
| C.2  | Template does not exist: Return error response |
| D    | Replace placeholders with actual data |
| E    | Save modified document as DOCX |
| F    | Convert saved DOCX file to PDF |
| G    | Send generated PDF as response |


## Table of Contents
- [Installation](#installation)
- [Usage](#usage)
  - [Endpoints](#endpoints)
  - [Request Format](#request-format)
  - [Response Format](#response-format)
- [Project Structure](#project-structure)

## Installation

To install and run the project locally, follow these steps:

1. Clone the repository:
   ```bash
   git clone https://github.com/TongTunyarat/poc-generate-document.git
   cd poc-generate-document
   ```

2. Modify `application.properties`:
   - Change the database configuration to your database
   ```properties
   spring.datasource.url=jdbc:sqlite:C:/.../template.db
   spring.datasource.driver-class-name=org.sqlite.JDBC
   spring.jpa.database-platform=org.hibernate.community.dialect.SQLiteDialect
   ```

3. Build the project:
   ```bash
   mvn clean install
   ```

4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

## Usage

### Endpoints

- **URL**: `http://localhost:8080/generate`
- **Method**: `POST`
- **Content-Type**: `application/json`
- **Description**: Generates a document based on the provided template and data, and returns a PDF file.

### Request Format

```json
{
  "requestID" : "INV0001",
  "documentType" : "OR",
  "content" : {
    "name": "คุณกฤติวัช  สุรศักดิ์พิพัฒน์",
    "num": "",
    "no": "",
    "date": "10/6/2567",
    "cg": "X",
    "nb": "",
    "cc": "",
    "pm": "",
    "wd": "X",
    "pc": "",
    "oth1": "",
    "pd": "",
    "as": "X",
    "ep": "",
    "oth2": "",
    "item1": {
      "description1": "ค่าอาหารและเครื่องดื่ม รับรองลูกค้า วันที่ 31/5/2567",
      "pack1": "",
      "qty1": "",
      "price1": "",
      "amount1": "1366.35"
    },
    "item2": {
      "description2": "ค่าเดินทาง",
      "pack2": "",
      "qty2": "",
      "price2": "",
      "amount2": "2500.00"
    },
    "item3": {
      "description3": "",
      "pack3": "",
      "qty3": "",
      "price3": "",
      "amount3": ""
    },		
    "item4": {
      "description4": "",
      "pack4": "",
      "qty4": "",
      "price4": "",
      "amount4": ""
    },
    "item5": {
      "description5": "",
      "pack5": "",
      "qty5": "",
      "price5": "",
      "amount5": ""
    },		
    "item6": {
      "description6": "",
      "pack6": "",
      "qty6": "",
      "price6": "",
      "amount6": ""
    },	
    "item7": {
      "description7": "",
      "pack7": "",
      "qty7": "",
      "price7": "",
      "amount7": ""
    },
    "item8": {
      "description8": "",
      "pack8": "",
      "qty8": "",
      "price8": "",
      "amount8": ""
    },
    "item9": {
      "description9": "",
      "pack9": "",
      "qty9": "",
      "price9": "",
      "amount9": ""
    },
    "item10": {
      "description10": "",
      "pack10": "",
      "qty10": "",
      "price10": "",
      "amount10": ""
    },
    "amount": "1,366.35",
    "vat": "95.65",		
    "total": "1462.00",
    "remark": "ใช้บัตร corporate card คุณกฤติวัช",		
    "vendor": "บริษัท กินซ่า เอ แอนด์ ที จำกัด",
    "referTo": "MIYABI KAPPO",
    "requiredDate": "10/6/2567",
    "paymentDate": "16/6/2567",
    "deliveryLocation": "99/99 salaya",
    "contactName": "คุณ... ...",
    "requisitioner": "ชัญญานุช แสงบัวงามล้ำ",
    "department": "R&D",
    "approver": "คุณกฤติวัช สุรศักดิ์พิพัฒน์",
    "approved": "คุณ... ...",
    "comment": "testttttttttttttttttttt"
  }
}
```

### Response Format

- **Content-Type**: `application/pdf`
- **Description**: The response will be a PDF document generated from the provided data and template.
- **Example**:

  ![image](https://github.com/TongTunyarat/poc-generate-document/assets/113578736/c4fd35ec-9ff1-4f3f-8fc6-c5b9edc26cba)

## Project Structure

- `src/main/java/com/example/training/service/DocxService.java`: Contains the service logic for generating and converting documents.
- `src/main/resources/templates`: Directory where DOCX templates are stored.
