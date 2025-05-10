import { W as E, X as w, T as d, w as l, U as c, Y as M, Z as D, Q as T, M as R, b as C, _ as L, m as v } from "./copilot-Cn85U7pa.js";
import { r as y } from "./state-BhzPx_8H.js";
import { B as I } from "./base-panel-__I0hBSP.js";
import { i as n } from "./icons-Dw_MbfTe.js";
const _ = "copilot-log-panel{padding:var(--space-100);font:var(--font-xsmall);display:flex;flex-direction:column;gap:var(--space-50);overflow-y:auto}copilot-log-panel .row{display:flex;align-items:flex-start;padding:var(--space-50) var(--space-100);border-radius:var(--radius-2);gap:var(--space-100)}copilot-log-panel .row.information{background-color:var(--blue-50)}copilot-log-panel .row.warning{background-color:var(--yellow-50)}copilot-log-panel .row.error{background-color:var(--red-50)}copilot-log-panel .type{margin-top:var(--space-25)}copilot-log-panel .type.error{color:var(--red)}copilot-log-panel .type.warning{color:var(--yellow)}copilot-log-panel .type.info{color:var(--color)}copilot-log-panel .message{display:flex;flex-direction:column;flex-grow:1;gap:var(--space-25);overflow:hidden}copilot-log-panel .message>*{white-space:nowrap}copilot-log-panel .firstrow{display:flex;align-items:baseline;gap:.5em;flex-direction:column}copilot-log-panel .firstrowmessage{width:100%}copilot-log-panel button{padding:0;border:0;background:transparent}copilot-log-panel svg{height:12px;width:12px}copilot-log-panel .secondrow,copilot-log-panel .timestamp{font-size:var(--font-size-0);line-height:var(--line-height-1)}copilot-log-panel .expand span{height:12px;width:12px}";
var S = Object.defineProperty, b = Object.getOwnPropertyDescriptor, g = (e, a, t, s) => {
  for (var o = s > 1 ? void 0 : s ? b(a, t) : a, p = e.length - 1, i; p >= 0; p--)
    (i = e[p]) && (o = (s ? i(a, t, o) : i(o)) || o);
  return s && o && S(a, t, o), o;
};
class B {
  constructor() {
    this.showTimestamps = !1, L(this);
  }
  toggleShowTimestamps() {
    this.showTimestamps = !this.showTimestamps;
  }
}
const h = new B();
let r = class extends I {
  constructor() {
    super(...arguments), this.unreadErrors = !1, this.messages = [], this.nextMessageId = 1, this.transitionDuration = 0;
  }
  connectedCallback() {
    super.connectedCallback(), this.onCommand("log", (e) => {
      this.handleLogEventData({ type: e.data.type, message: e.data.message });
    }), this.onEventBus("log", (e) => this.handleLogEvent(e)), this.onEventBus("update-log", (e) => this.updateLog(e.detail)), this.onEventBus("notification-shown", (e) => this.handleNotification(e)), this.onEventBus("clear-log", () => this.clear()), this.transitionDuration = parseInt(
      window.getComputedStyle(this).getPropertyValue("--dev-tools-transition-duration"),
      10
    ), E((e) => {
      this.log(e.type, e.message, !!e.internal, e.details, e.link);
    }), w.forEach((e) => {
      this.log(e.type, e.message, !!e.internal, e.details, e.link);
    }), w.length = 0;
  }
  clear() {
    this.messages = [];
  }
  handleNotification(e) {
    this.log(e.detail.type, e.detail.message, !0, e.detail.details, e.detail.link, void 0);
  }
  handleLogEvent(e) {
    this.handleLogEventData(e.detail);
  }
  handleLogEventData(e) {
    this.log(
      e.type,
      e.message,
      !!e.internal,
      e.details,
      e.link,
      d(e.expandedMessage),
      d(e.expandedDetails),
      e.id
    );
  }
  activate() {
    this.unreadErrors = !1, this.updateComplete.then(() => {
      const e = this.renderRoot.querySelector(".message:last-child");
      e && e.scrollIntoView();
    });
  }
  render() {
    return l`<style>
        ${_}
      </style>
      ${this.messages.map((e) => this.renderMessage(e))} `;
  }
  renderMessage(e) {
    let a, t, s;
    return e.type === c.ERROR ? (a = "error", s = n.exclamationMark, t = "Error") : e.type === c.WARNING ? (a = "warning", s = n.warning, t = "Warning") : (a = "info", s = n.info, t = "Info"), e.internal && (a += " internal"), l`
      <div class="row ${e.type} ${e.details || e.link ? "has-details" : ""}">
        <span class="type ${a}" title="${t}">${s}</span>
        <div class="message" @click=${() => this.toggleExpanded(e)}>
          <span class="firstrow">
            <span class="timestamp" ?hidden=${!h.showTimestamps}>${W(e.timestamp)}</span>
            <span class="firstrowmessage"
              >${e.expanded && e.expandedMessage ? e.expandedMessage : e.message}
            </span>
          </span>
          ${e.expanded ? l` <span class="secondrow">${e.expandedDetails}</span>` : l`<span class="secondrow" ?hidden="${!e.details && !e.link}"
                >${d(e.details)}
                ${e.link ? l`<a class="ahreflike" href="${e.link}" target="_blank">Learn more</a>` : ""}</span
              >`}
        </div>
        <button
          aria-label="Expand details"
          theme="icon tertiary"
          class="expand"
          @click=${() => this.toggleExpanded(e)}
          ?hidden=${!e.expandedDetails}>
          <span>${e.expanded ? n.chevronDown : n.chevronRight}</span>
        </button>
      </div>
    `;
  }
  log(e, a, t, s, o, p, i, $) {
    const k = this.nextMessageId;
    this.nextMessageId += 1;
    const u = M(a, 200);
    u !== a && !i && (i = a);
    const m = {
      id: k,
      type: e,
      message: u,
      details: s,
      link: o,
      dontShowAgain: !1,
      deleted: !1,
      expanded: !1,
      expandedMessage: p,
      expandedDetails: i,
      timestamp: /* @__PURE__ */ new Date(),
      internal: t,
      userId: $
    };
    for (this.messages.push(m); this.messages.length > r.MAX_LOG_ROWS; )
      this.messages.shift();
    return this.requestUpdate(), this.updateComplete.then(() => {
      const f = this.renderRoot.querySelector(".message:last-child");
      f ? (setTimeout(() => f.scrollIntoView({ behavior: "smooth" }), this.transitionDuration), this.unreadErrors = !1) : e === c.ERROR && (this.unreadErrors = !0);
    }), m;
  }
  updateLog(e) {
    let a = this.messages.find((t) => t.userId === e.id);
    a || (a = this.log(c.INFORMATION, "<Log message to update was not found>", !1)), Object.assign(a, e), D(a.expandedDetails) && (a.expandedDetails = d(a.expandedDetails)), this.requestUpdate();
  }
  toggleExpanded(e) {
    e.expandedDetails && (e.expanded = !e.expanded, this.requestUpdate()), T("use-log", { source: "toggleExpanded" });
  }
};
r.MAX_LOG_ROWS = 1e3;
g([
  y()
], r.prototype, "unreadErrors", 2);
g([
  y()
], r.prototype, "messages", 2);
r = g([
  v("copilot-log-panel")
], r);
let x = class extends R {
  createRenderRoot() {
    return this;
  }
  connectedCallback() {
    super.connectedCallback(), this.style.display = "flex";
  }
  render() {
    return l`
      <button title="Clear log" aria-label="Clear log" theme="icon tertiary">
        <span
          @click=${() => {
      C.emit("clear-log", {});
    }}
          >${n.trash}</span
        >
      </button>
      <button title="Toggle timestamps" aria-label="Toggle timestamps" theme="icon tertiary">
        <span
          class="${h.showTimestamps ? "on" : "off"}"
          @click=${() => {
      h.toggleShowTimestamps();
    }}
          >${n.clock}</span
        >
      </button>
    `;
  }
};
x = g([
  v("copilot-log-panel-actions")
], x);
const P = {
  header: "Log",
  expanded: !0,
  panelOrder: 0,
  panel: "bottom",
  floating: !1,
  tag: "copilot-log-panel",
  actionsTag: "copilot-log-panel-actions"
}, A = {
  init(e) {
    e.addPanel(P);
  }
};
window.Vaadin.copilot.plugins.push(A);
const N = { hour: "numeric", minute: "numeric", second: "numeric", fractionalSecondDigits: 3 }, q = new Intl.DateTimeFormat(navigator.language, N);
function W(e) {
  return q.format(e);
}
export {
  x as Actions,
  r as CopilotLogPanel
};
