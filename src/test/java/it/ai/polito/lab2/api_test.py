import requests

students = "http://localhost:8080/API/students/"
courses = "http://localhost:8080/API/courses/"


def addStudent(id, firstName, name, token):
    json = {"id": id, "firstName": firstName, "name": name}
    print("POST TO: " + students)
    return requests.post(students, json=json, headers=get_headers(token))


def addCourse(name, min, max, token):
    json = {"name": name, "min": min, "max": max}
    print("POST TO: " + courses)
    return requests.post(courses, json=json, headers=get_headers(token))


def enrollOne(course, studentId, token):
    url = f"{courses}{course}/enrollOne"
    print("POST TO: " + url)
    return requests.post(url, data=studentId, headers=get_headers(token))


def proposeTeam(courseName, teamName, memberIds, token):
    url = f"{courses}{courseName}/proposeTeam"
    print("POST TO: " + url)
    json = {"teamName": teamName, "memberIds": list(memberIds)}
    return requests.post(url, json=json, headers=get_headers(token))


def enableDisableCourse(courseName, available, token):
    uri = "enable" if available is True else "disable"
    url = f"{courses}{courseName}/{uri}"
    print("PUT TO: " + url)
    return requests.put(url, headers=get_headers(token))


def authenticate(username, password):
    url = "http://localhost:8080/authenticate"
    print("POST TO: " + url)
    return requests.post(url, json={"username": username, "password": password})


def get_headers(token):
    return {"Content-Type": "application/json", "Authorization": f"Bearer {token}"}


def me(token):
    url = "http://localhost:8080/me"
    return requests.get(url, headers=get_headers(token))


def all_courses(token):
    url = courses
    return requests.get(url, headers=get_headers(token))


def authenticate_users(s_account, d_account):
    d1 = authenticate(d_account['username'], d_account['password'])
    s1 = authenticate(s_account['username'], s_account['password'])
    admin = authenticate("admin", "admin")
    print(d1.text)
    print(s1.text)
    print(admin.text)


def addProfessor(id, firstName, name, token):
    url = "http://localhost:8080/API/admin/addProfessor"
    json = {"id": id, "firstName": firstName, "name": name}
    print("POST TO: " + url)
    return requests.post(url, json=json, headers=get_headers(token))


if __name__ == '__main__':
    s1_account = {"username": "s1@studenti.polito.it", "password": "(F3w!y7ISA"}
    d1_account = {"username": "d1@polito.it", "password": "+iUr50!BB2"}
    d2_account = {"username": "d2@polito.it", "password": "#5T*0Rzg5p"}
    admin_token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaWF0IjoxNTg5NjQ1OTc3LCJleHAiOjE1ODk2NDk1Nzd9.fbu0-rr3TTZN81Zk3C1TCDOWT1sQcUpgMoFXUCiYJC8"
    d1_token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkMUBwb2xpdG8uaXQiLCJyb2xlcyI6WyJST0xFX1BST0ZFU1NPUiJdLCJpYXQiOjE1ODk2NDU5NzcsImV4cCI6MTU4OTY0OTU3N30.THfbm5Zq-qU-oR-JrQ7Yc8P-BM9MAb4rwd6kYEvT01Y"
    s1_token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzMUBzdHVkZW50aS5wb2xpdG8uaXQiLCJyb2xlcyI6WyJST0xFX1NUVURFTlQiXSwiaWF0IjoxNTg5NjQ1OTc3LCJleHAiOjE1ODk2NDk1Nzd9.00VjGSeOjdG1P2M_88zX0X0z1e7kIfi959O5skatywE"
    d2_token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkMkBwb2xpdG8uaXQiLCJyb2xlcyI6WyJST0xFX1BST0ZFU1NPUiJdLCJpYXQiOjE1ODk2NDYwODgsImV4cCI6MTU4OTY0OTY4OH0.PUmuh2mu6WIFJKgt3RV5fTHUxVFwN_suZ1v1BD1mljg"
    s3_token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzM0BzdHVkZW50aS5wb2xpdG8uaXQiLCJyb2xlcyI6WyJST0xFX1NUVURFTlQiXSwiaWF0IjoxNTg5NjQ1ODI2LCJleHAiOjE1ODk2NDk0MjZ9.aQeVEUD_lB3sPAKVM_2MI_zuxGHoVR4O1x-b8po3z1w"

    res = enableDisableCourse("Machine Learning", False, d2_token)
    print(res.status_code)
    print(res.text)
