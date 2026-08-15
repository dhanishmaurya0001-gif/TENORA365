# Commercial launch checklist

## Business
- [ ] Decide school pricing and limits
- [ ] Terms of service / privacy policy / refund policy
- [ ] GST/invoicing requirements checked with a professional
- [ ] Support email and support process

## Technical
- [ ] Domain DNS points to VPS
- [ ] HTTPS active
- [ ] Secrets rotated from defaults
- [ ] MySQL not publicly exposed
- [ ] Daily backup + offsite copy
- [ ] Restore drill passed
- [ ] Two-school tenant isolation test passed
- [ ] Payment webhook signature verification tested
- [ ] Notification provider delivery/opt-out tested
- [ ] Error monitoring configured
- [ ] Disk/RAM/CPU monitoring configured
- [ ] Rate limiting/WAF added at the edge for public launch
