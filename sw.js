const C='pcs-shell-v21',A=['./','./index.html','./styles.css','./app.js','./manifest.webmanifest','./icon.svg','./game.html'];
self.addEventListener('install',e=>e.waitUntil(caches.open(C).then(c=>c.addAll(A)).then(()=>self.skipWaiting())));
self.addEventListener('activate',e=>e.waitUntil(Promise.all([caches.keys().then(keys=>Promise.all(keys.filter(k=>k!==C).map(k=>caches.delete(k)))),self.clients.claim()])));
self.addEventListener('fetch',e=>{
  if(e.request.method!=='GET'||new URL(e.request.url).origin!==location.origin)return;
  const url=new URL(e.request.url);
  const isShellFile=url.pathname.endsWith('/index.html')||url.pathname.endsWith('/app.js')||url.pathname.endsWith('/styles.css')||url.pathname.endsWith('/sw.js');
  if(isShellFile){
    e.respondWith(fetch(e.request,{cache:'no-cache'}).then(response=>{
      if(response.ok){const copy=response.clone();caches.open(C).then(c=>c.put(e.request,copy));}
      return response;
    }).catch(()=>caches.match(e.request).then(cached=>cached||caches.match('./index.html'))));
    return;
  }
  e.respondWith(caches.match(e.request).then(cached=>cached||fetch(e.request).then(response=>{
    if(response.ok){const copy=response.clone();caches.open(C).then(c=>c.put(e.request,copy));}
    return response;
  }).catch(()=>caches.match('./index.html'))));
});
