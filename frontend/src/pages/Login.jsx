import {useState} from 'react';
import {motion} from 'framer-motion';
import TenRings from '../components/TenRings';
import api from '../lib/api';
const demo = import.meta.env.VITE_DEMO_MODE === 'true';
export default function Login({onLogin}) {
  const [u,setU]=useState(demo?'admin@demo.tenora365.com':'');
  const [p,setP]=useState(demo?'Demo@School2026!':'');
  const [err,setErr]=useState(''),[busy,setBusy]=useState(false);
  async function signIn(username,password) {
    setBusy(true);setErr('');
    try {
      const {data}=await api.post('/auth/login',{username,password});
      localStorage.setItem('tenora_access',data.accessToken);
      localStorage.setItem('tenora_user',JSON.stringify(data.user));
      onLogin(data.user);
    } catch(x) {setErr(x.response?.data?.error||'Login failed. Please try again.');}
    finally {setBusy(false);}
  }
  return <div className="login"><div className="login-art"><TenRings/>
    <motion.div initial={{opacity:0,y:20}} animate={{opacity:1,y:0}}>
      <span className="eyebrow">MULTI-SCHOOL ADMINISTRATION DEMO</span>
      <h1>Every school.<br/><em>One workspace.</em></h1>
      <p>Explore school tenants, student records, teacher records, fees and PDF receipts.</p>
      {demo&&<p>Fictional shared data resets when the free host restarts. Operational module workspaces are prototypes; no real payments or messages are sent.</p>}
    </motion.div></div>
    <form className="login-card" onSubmit={e=>{e.preventDefault();signIn(u,p)}}>
      <div className="mini-logo"><TenRings small/></div><h2>Welcome to TENORA365</h2>
      <p>Sign in to your school workspace.</p>{err&&<div role="alert" className="error">{err}</div>}
      {demo&&<><button type="button" className="primary" disabled={busy} onClick={()=>signIn('admin@demo.tenora365.com','Demo@School2026!')}>Open demo school</button>
        <button type="button" className="ghost" disabled={busy} onClick={()=>signIn('superadmin','Demo@School2026!')}>Explore platform administrator</button>
        <button type="button" className="ghost" disabled={busy} onClick={()=>signIn('admin@riverbank.example','Demo@School2026!')}>Open second school</button></>}
      <label>Email / Username<input required value={u} onChange={e=>setU(e.target.value)}/></label>
      <label>Password<input required type="password" value={p} onChange={e=>setP(e.target.value)}/></label>
      <button className="primary" disabled={busy}>{busy?'Entering...':'Sign in'}</button>
      {demo&&<small className="hint">Public demo password: Demo@School2026!</small>}
    </form></div>;
}
