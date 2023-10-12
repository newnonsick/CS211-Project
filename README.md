# CS211-661-Project
> Project ภาคต้น 2566

## Project detail
https://saacsos.notion.site/Project-CS211-2566-1c8e195c0ebd4071aea8f733692e3989?pvs=4

## วิธีการติดตั้งหรือรันโปรแกรม

## ตัวอย่างข้อมูลผู้ใช้ระบบ (username, password) 
   


## การวางโครงสร้างไฟล์
```
+---.idea (เก็บข้อมูลการตั้งค่าและข้อมูลเฉพาะของโปรเจค)
+---.mvn (เป็นตัวช่วยให้ผู้ใช้สามารถรัน Maven ได้)
+---data (เก็บรูปภาพของอีเวนต์ รูปภาพโปรไฟล์ และข้อมูลที่เป็น csv file)
|   +---eventPicture
|   \---profile_picture
+---document (เก็บเอกสารข้อมูลรายละเอียดที่เกี่ยวข้องกับโครงงาน และรูปภาพ UML Class Diagram)
+---src (เก็บข้อมูลที่เกี่ยวข้องกับตัวโปรแกรม controller, models, services, รูปภาพ, หน้า.fxml และ stylesheet.css)
   \---main
       +---java
       |   \---cs211
       |       \---project
       |           +---controllers (ใช้เก็บคลาสcontroller ทั้งหมด)
       |           +---cs211661project (ใช้เก็บตัว MainApplication )
       |           +---models (ใช้เก็บคลาส model และคลาส collection ทั้งหมด)
       |           |   \---collections
       |           \---services (ใช้เก็บคลาส service ทั้งหมด)
       \---resources (เก็บในส่วน .fxml css และรูปภาพประกอบภายใน application)
           \---cs211
               \---project
                   +---images (ใช้เก็บรูปภาพประกอบภายใน application)
                   +---style (เก็บ stylesheet.css)
                   \---views (ก็บ .fxml file ทั้งหมด)

```
## สิ่งที่พัฒนาในแต่ละครั้งที่ส่งความก้าวหน้าของระบบ และครั้งที่ส่งโครงงานที่สมบูรณ์

#### นางสาว พิมพ์มาดา ปานงาม (นัทธ์) 6510405717
- ความก้าวหน้าของระบบครั้งที่ 1
  สร้างแผนผังแสดงสิ่งที่ควรมีในโปรแกรมโดยคร่าว ๆ แบ่งงานระหว่างสมาชิก ออกแบบหน้า UI และทำ fxml ในส่วน event information, create event, my events และ user information รวมถึงเขียนโค้ดเพื่อเชื่อมโยงระหว่างหน้าที่มีความเกี่ยวข้องกัน
- ความก้าวหน้าของระบบครั้งที่ 2
  สร้าง models ของ Event และ EventList และเขียน UserInformationController ให้อ่านไฟล์ข้อมูลผู้ใช้และแสดงข้อมูลในหน้า fxml ของ user information ได้
- ความก้าวหน้าของระบบครั้งที่ 3
  เขียน JoinEventFileDataSource เพื่อให้อ่านและเขียนไฟล์การเข้าร่วมอีเวนต์ของผู้ใช้ได้ อัพเดต UserInformation Controller ให้แสดงรายการอีเวนต์ แสดงรูปโปรไฟล์ของผู้ใช้ และเขียนให้ผู้ใช้สามารถเปลี่ยนรูปโปรไฟล์หรือเปลี่ยนเป็นภาพ default ได้ อัพเดต CreateEventController ให้สาามารถสร้างอีเวนต์โดยรับข้อมูลจากผู้ใช้และเพิ่มอีเวนต์ไปยังไฟล์ที่เก็บข้อมูลได้ อัพเดต อัพเดต MyEventsController ให้แสดงอีเวนต์ที่ผู้ใช้สร้าง และอัพเดต EventInformationController ให้อ่านไฟล์และแสดงข้อมูลของแต่ละอีเวนต์ รวมถึงเขียนให้ผู้ใช้สามารถเข้าร่วมอีเวนต์ได้
- การส่งโครงงานที่สมบูรณ์
  เพิ่มหน้า fxml สำหรับการรีเซ็ตรหัสผ่านและทำให้ผู้ใช้สามารถรีเซ็ตรหัสผ่านได้ และปรับปรุงหน้า UI ให้มีความสวยงามและเป็นระเบียบขึ้น เพิ่มข้อมูลผู้ใช้ในระบบ ปรับปรุงรายละเอียดของโปรแกรมและแก้ไขข้อผิดพลาดในบางจุด 

#### นางสาว เพรียงขวัญ ฉัตรชฎานุกูล (ไอซ์) 6510405725
 - ความก้าวหน้าของระบบครั้งที่ 1
   ทำความเข้าใจโครงสร้างของโครงงานในภาพรวม เข้าใจในส่วนที่ตนเองต้องรับผิดชอบ สามารถศึกษาและเข้าใจการออกแบบรูปแบบของโครงงาน การเชื่อมโยงหน้า ออกแบบ GUI ให้สามารถใช้งานได้ง่ายต่อผู้ใช้ 
 - ความก้าวหน้าของระบบครั้งที่ 2
   มีการสร้างคลาส(models) Event, EventList, Activity, ActivityList(ในส่วนกิจกรรมของผู้เข้าร่วมอีเวนต์) เพื่อให้สามารถนำไปใช้งานต่อได้ในส่วนต่างๆ ของระบบได้  เขียนโค้ดเพื่อใช้ในการอ่านไฟล์ของอีเวนต์
 - ความก้าวหน้าของระบบครั้งที่ 3
    พัฒนาในส่วนของ Controller EventManagementController, EventParticipantManagementController และ ParticipantActivityController เป็นหลัก รวมถึงส่วนที่ใช้ในการอ่านและเขียนไฟล์ โดยพัฒนาให้ผู้ใช้สามารถแก้ไขข้อมูลของตนเองได้ และสามารถจัดการกับส่วนอื่นๆ ที่เกี่ยวข้องกับผู้เข้าร่วมได้ รวมถึงส่วนที่เกี่ยวข้องกับการแสดงผลของอีเวนต์สำหรับผู้เข้าร่วม
 - การส่งโครงงานที่สมบูรณ์
   พัฒนาและแก้ไขจุดบกพร่องต่างๆ ที่ใช้ในการสร้างอีเวนต์และการเข้าร่วมอีเวนต์ให้ทำงานได้อย่างมีเสถียรภาพมากขึ้น กำหนดเงื่อนไขการทำงานของระบบ ปรับการแสดงผลให้ง่ายต่อผู้ใช้ และแก้ไขให้ทำงานได้ดียิ่งขึ้น 

#### นาย ฐิติวัจน์ มงคลกิตติโชติ (นิว) 6510405440
 - ความก้าวหน้าของระบบครั้งที่ 1
   ทำ UI ของหน้าที่รับผิดชอบคล่าวๆ ทำ fxml team-management, team-communication, myteam-list, team-list, event-team-management, create-team และสร้าง Controller ของแต่ละ fxml ให้เชื่อมต่อกับหน้าอื่นๆที่เกี่ยวข้องกันได้
 - ความก้าวหน้าของระบบครั้งที่ 2
   สร้าง models ของ Team, TeamList, Activity(ในส่วนกิจกรรมของทีม) และ ActivityList(ในส่วนกิจกรรมของทีม) และสร้าง teamListFileDataSource เพื่อให้อ่านและเขียนไฟล์ข้อมูลทีมได้ และทำในส่วนของการ search event ให้สมารถค้นหาได้ และปรับปรุงในส่วนของ showevent ให้มีประสิทภาพมากขึ้น
 - ความก้าวหน้าของระบบครั้งที่ 3
   ทำ Controller ของหน้าที่รับผิดชอบ CreateTeamController, MyTeamListController, TeamCommunicationController, TeamElementController, TeamOfEventListController, TeamParticipantElement ให้สามารถทำงานได้ถูกต้อง และทำให้หน้า UI ของทุกๆหน้ามีความสวยงามและเป็นระเบียบขึ้น และเพิ่ม models ของ TeamParticipantList และ TeamParticipant และสร้าง teamParticipantListFileDataSource เพื่อให้อ่านและเขียนไฟล์ข้อมูลผู้เข้าร่วมทีมได้ และสร้าง models TeamChat และ TeamChatList และสร้าง teamChatListFileDataSource เพื่อให้อ่านและเขียนไฟล์ข้อมูลการสนทนาของทีมได้ แก้โค้ดในส่วนของ category event ให้สามารถใช้งานกับ search event ได้
 - การส่งโครงงานที่สมบูรณ์
   ทำให้ระบบสามารถทำงานได้ถูกต้อง และแก้ไขจุดบกพร่องต่างๆ ที่ใช้ในการสร้างทีมและการเข้าร่วมทีมให้ทำงานได้อย่างมีเสถียรภาพมากขึ้น และแก้ไขให้ทำงานได้ดียิ่งขึ้น และปรับปรุงรายละเอียดของโปรแกรมและแก้ไขข้อผิดพลาดในบางจุด และทำ UI ให้สวยงามและเป็นระเบียบมากขึ้น และเพิ่มการ sort ข้อมูลของ model ต่างๆโดยใช้ comparator

#### นาย จิรายุ โออุไร (ตาล) 6510405407
 - ความก้าวหน้าของระบบครั้งที่ 1
   ทำส่วนของ FXRouterPane, เริ่มทำส่วนของการ Login และ Register ที่ใช้การได้, เริ่มทำหน้าเพจแรกและ Navigation Bar, เริ่มโครงหน้าของ Admin, DataSource ของ User
 - ความก้าวหน้าของระบบครั้งที่ 2
   อัปโหลดรูปโปรไฟล์, ส่วนของการโชว์หน้า Event List ให้แสดงออกมาเป็นตาราง, model user, model userlist, แก้ไขบัคและเพิ่มเติมส่วนต่างๆ
 - ความก้าวหน้าของระบบครั้งที่ 3
   การเก็บข้อมูลการ Login, ทำหน้า Admin Page จนใช้การได้อย่างเกือบสมบูรณ์, model LogUser, model LogUserList, DataSource ของ LogUser, Refactor โค้ดเพื่อให้ถูกหลักการเขียนโปรแกรมมากขึ้น, ส่วนของการค้นหา Event โดยใช้ Category, Animation ของ Navigation Bar
 - การส่งโครงงานที่สมบูรณ์
   ตกแต่งส่วนของที่ตนเคยรับผิดชอบ, แก้ไขบัคของส่วนต่างๆ, ปรับการแสดงผลตัวอักษรให้ไปปในทางเดียวกันมากขึ้น, เพิ่มฟังก์ชันให้ส่วนต่างๆ ได้แก่ ส่วนของ Admin, Register ซึ่งจะเกี่ยวกับการเข้าสู่ระบบและการเปลี่ยนรหัสผ่าน