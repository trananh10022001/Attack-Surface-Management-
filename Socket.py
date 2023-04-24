import websockets
import asyncio
import main
import DB_Connection
from datetime import datetime

async def scan(websocket):
    name = await websocket.recv()
    main.scanDomain(name)
    cursor = DB_Connection.cursor
    query0 = "SELECT COUNT(*) FROM asm.domain WHERE domain.domain_name = '"+str(name)+"' ORDER BY domain.id DESC LIMIT 1;"
    data = cursor.execute(query0)
    for i in cursor.fetchone():
        id_domain = i
    query1 = "UPDATE asm.domain SET status = 0 WHERE id = "+str(id_domain)
    cursor.execute(query1)
    now = datetime.now()
    query2 = "UPDATE asm.domain SET end_time = '"+str(now)+"' WHERE id = "+str(id_domain)
    cursor.execute(query2)
    DB_Connection.connection.commit()
    await websocket.send("true")
    

start_server = websockets.serve(scan, "localhost", 1337)
asyncio.get_event_loop().run_until_complete(start_server)
asyncio.get_event_loop().run_forever()
