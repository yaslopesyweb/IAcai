import { e as i, b as n, u as d } from "./copilot-Cn85U7pa.js";
const a = 5e3;
let o = 1;
function m(s) {
  i.notifications.includes(s) && (s.dontShowAgain && s.dismissId && u(s.dismissId), i.removeNotification(s), n.emit("notification-dismissed", s));
}
function f(s) {
  return d.getDismissedNotifications().includes(s);
}
function u(s) {
  f(s) || d.addDismissedNotification(s);
}
function r(s) {
  return !(s.dismissId && (f(s.dismissId) || i.notifications.find((t) => t.dismissId === s.dismissId)));
}
function N(s) {
  r(s) && c(s);
}
function c(s) {
  const t = o;
  o += 1;
  const e = { ...s, id: t, dontShowAgain: !1, animatingOut: !1 };
  i.setNotifications([...i.notifications, e]), !s.link && !s.dismissId && setTimeout(() => {
    m(e);
  }, s.delay ?? a), n.emit("notification-shown", s);
}
export {
  m as dismissNotification,
  N as showNotification
};
