import {useEffect,useState} from 'react';
import {Plus,Trash2,RefreshCw,Download} from 'lucide-react';
import api from '../lib/api';
const examples={
  teachers:{employeeNo:'DEMO-T003',fullName:'Demo Teacher',subject:'Science',email:'teacher@example.com',phone:'0000000000',salary:30000},
  fees:{studentId:1,amount:2500,feeHead:'Tuition - Demo',status:'PAID',provider:'DEMO_CASH'},
  subscriptions:{planCode:'STARTER',billingCycle:'MONTHLY',amount:999}
};
export default function ModulePage({module,title}) {
  const [rows,setRows]=useState([]),[text,setText]=useState(''),[busy,setBusy]=useState(false),[error,setError]=useState('');
  const direct=['teachers','fees','audit','subscriptions'].includes(module);
  const readOnly=module==='audit';
  function display(record){if(!record.dataJson)return JSON.stringify(record,null,2);try{return JSON.stringify(JSON.parse(record.dataJson),null,2)}catch{return record.dataJson}}
  const endpoint=module==='subscriptions'?'/commercial/subscriptions':direct?'/'+module:'/modules/'+module;
  async function load() {
    setBusy(true);setError('');
    try {setRows((await api.get(endpoint)).data);}
    catch(e) {setError(e.response?.data?.error||'Could not load records.');}
    finally {setBusy(false);}
  }
  useEffect(()=>{setText('');load();},[module]);
  async function add() {
    let data;try{data=JSON.parse(text||'{}');}catch{setError('Enter a valid JSON object.');return;}
    try{await api.post(endpoint,data);setText('');load();}
    catch(e){setError(e.response?.data?.error||'Could not save the record.');}
  }
  async function remove(id) {
    if(!confirm('Delete this fictional record?'))return;
    try{await api.delete(endpoint+'/'+id);load();}
    catch(e){setError(e.response?.data?.error||'Could not delete the record.');}
  }
  async function receipt(id) {
    try{
      const {data}=await api.get('/pdf/fee-receipt/'+id,{responseType:'blob'});
      const url=URL.createObjectURL(data),a=document.createElement('a');a.href=url;a.download='demo-receipt-'+id+'.pdf';a.click();URL.revokeObjectURL(url);
    }catch{setError('Could not generate the PDF receipt.');}
  }
  return <div className="panel"><div className="panel-head"><div><h2>{title}</h2>
    <p>{direct?'Structured '+title.toLowerCase()+' records.':'Prototype workspace: save and review tenant-scoped JSON records. Full '+title.toLowerCase()+' automation is not implemented.'}</p>
    </div><button className="ghost" onClick={load}><RefreshCw size={16}/>Refresh</button></div>
    {error&&<div role="alert" className="error">{error}</div>}
    {!readOnly&&<div className="composer"><textarea value={text} onChange={e=>setText(e.target.value)} placeholder={JSON.stringify(examples[module]||{name:'Fictional demo record',status:'Draft'})}/>
      <button className="ghost" onClick={()=>setText(JSON.stringify(examples[module]||{name:'Fictional demo record',status:'Draft'},null,2))}>Fill example</button>
      <button className="primary" disabled={busy} onClick={add}><Plus size={16}/>Add</button></div>}
    <div className="records">{rows.map(r=><div className="record" key={r.id}><div><b>#{r.id}</b><pre>{display(r)}</pre></div>
      {module==='fees'&&<button className="ghost" onClick={()=>receipt(r.id)}><Download size={16}/>PDF receipt</button>}
      {!direct&&<button className="danger" onClick={()=>remove(r.id)}><Trash2 size={16}/></button>}
    </div>)}{!rows.length&&!busy&&<div className="empty">No records yet.</div>}</div></div>;
}
