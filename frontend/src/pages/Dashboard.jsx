import {useEffect,useState} from 'react';
import {motion} from 'framer-motion';
import {Users,GraduationCap,Wallet,School} from 'lucide-react';
import api from '../lib/api';
import TenRings from '../components/TenRings';
export default function Dashboard(){
  const [d,setD]=useState({}),[error,setError]=useState('');
  const platform=JSON.parse(localStorage.getItem('tenora_user')||'{}').role==='SUPER_ADMIN';
  useEffect(()=>{(platform?api.get('/super/schools'):api.get('/dashboard')).then(r=>setD(platform?{schools:r.data.length}:r.data)).catch(()=>setError('Dashboard could not load. Please sign in again.'));},[]);
  const cards=platform?[['Client schools',d.schools,School],['Access','Platform administrator',Users]]:[['Students',d.students,Users],['Teachers',d.teachers,GraduationCap],['Fees collected',`INR ${Number(d.feesCollected||0).toLocaleString()}`,Wallet],['Workspace',d.school||'TENORA365',School]];
  return <><section className="hero"><div><span className="eyebrow">COMMAND CENTER</span><h2>{platform?'Manage school workspaces.':'Your school at a glance.'}</h2><p>Student, teacher and fee records are backed by the application database.</p></div><TenRings small/></section>
  {error&&<div role="alert" className="error">{error}</div>}
  <div className="cards">{cards.map(([t,v,I],i)=><motion.div initial={{opacity:0,y:12}} animate={{opacity:1,y:0}} transition={{delay:i*.05}} className="card" key={t}><I/><span>{t}</span><strong>{v??'...'}</strong></motion.div>)}</div>
  <div className="panel"><h3>Demonstration scope</h3><p>Explore school onboarding, student CRUD, teachers, fee entries, PDF receipts and audit records. Attendance, examinations, payroll, library and other operational modules currently provide JSON record workspaces.</p></div></>;
}
