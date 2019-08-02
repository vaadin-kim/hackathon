import { PolymerElement } from "@polymer/polymer/polymer-element.js";
import { html } from "@polymer/polymer/lib/utils/html-tag.js";
import "@vaadin/vaadin-grid/vaadin-grid.js";
import "@vaadin/vaadin-grid/vaadin-grid-column.js";


class NewsView extends PolymerElement {
  static get template() {
    return html`
      <style include="shared-styles">
        :host {
          display: block;
          height: 100%;
        }
      </style>

      <vaadin-grid
        id="list"
        items="[[persons]]"
        active-item="{{activeItem}}"
        theme="no-border no-row-borders"
        style="height:100%"
      >
        <vaadin-grid-column>
          <template>
            <div
              style="border-radius:var(--lumo-border-radius);box-shadow: var(--lumo-box-shadow-s);padding:var(--lumo-space-s);"
            >
              <h3 id="order" style="margin:0">
                [[item.lastname]], [[item.firstname]]
              </h3>
              <a theme="font-size-xs" id="date" href="mailto:[[item.email]]"
                >[[item.email]]</a
              >
            </div>
          </template>
        </vaadin-grid-column>
      </vaadin-grid>
    `;
  }

  static get is() {
    return "news-view";
  }

  static get properties() {
    return {
      activeItem: {
        observer: "_activeItemChanged",
      },
    };
  }

  _activeItemChanged(item) {
    this.$.list.selectedItems = item ? [item] : [];
  }
}

customElements.define(NewsView.is, NewsView);
