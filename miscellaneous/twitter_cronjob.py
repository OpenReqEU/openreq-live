#!/usr/bin/python
import MySQLdb
import sys
import requests


db = MySQLdb.connect("localhost", "root", "admin", "openreq_releaseplanning", charset='utf8')
cursor = db.cursor()

sql = "SELECT or_project.id, or_requirement.id, or_requirement.title, or_requirement.description FROM or_requirement, or_project WHERE or_requirement.project_id = or_project.id ORDER BY or_project.id ASC"
input_data = []

try:
    cursor.execute(sql)
    results = cursor.fetchall()
    input_data = {}
    for (project_id, requirement_id, title, description) in results:
        if project_id not in input_data:
            input_data[project_id] = []
        input_data[project_id] += [{ "id": str(requirement_id), "title": title, "description": description }]
except:
    print "Error: unable to fetch data"
    db.close()
    sys.exit(-1)

db.close()

for (project_id, input_dat) in input_data.iteritems():
    print input_dat
    r = requests.post("http://localhost:5000/popularity/hashtag/", json=input_dat)
    if r.status_code == 200:
        result = r.json()
        print result
        db = MySQLdb.connect("localhost", "root", "admin", "openreq_releaseplanning", charset='utf8')
        cursor = db.cursor()
        for r in result:
            requirement_id, popularity = r["id"], float(r["popularity"])/float(100)
            sql = """
               UPDATE or_requirement
               SET social_popularity='%s'
               WHERE id=%d
            """ % (str(popularity), int(requirement_id))
            print sql
            cursor.execute(sql)
        db.commit()
        db.close()

