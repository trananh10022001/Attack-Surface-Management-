import websockets
import asyncio
import main

async def scan(websocket, path):
    name = await websocket.recv()
    main.scanDomain(name)
    await websocket.send("true")

start_server = websockets.serve(scan, "localhost", 1337)

asyncio.get_event_loop().run_until_complete(start_server)
asyncio.get_event_loop().run_forever()
