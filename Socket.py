import websockets
import asyncio
import main
import DB_Connection
from datetime import datetime

async def scan(websocket):
    name = await websocket.recv()
    print('The target: '+name)
    main.scanDomain(name)
    cursor = DB_Connection.cursor
    query0 = "SELECT domain.id FROM asm.domain WHERE domain.domain_name = '"+str(name)+"' ORDER BY domain.id DESC LIMIT 1;"
    data = cursor.execute(query0)
    for i in cursor.fetchone():
        id_domain = i
    print(id_domain)
    query1 = "UPDATE asm.domain SET domain.status = 0 WHERE domain.id = '"+str(id_domain)+"'"
    cursor.execute(query1)
    now = datetime.now()
    query2 = "UPDATE asm.domain SET domain.end_time = '"+str(now)+"' WHERE domain.id = '"+str(id_domain)+"'"
    cursor.execute(query2)
    DB_Connection.connection.commit()
    await websocket.send("true")
    print('Success')
    

start_server = websockets.serve(scan, "localhost", 1337)
asyncio.get_event_loop().run_until_complete(start_server)
asyncio.get_event_loop().run_forever()
