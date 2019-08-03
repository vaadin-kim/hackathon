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
        
        .bulletin {
        	background-color: #40444B;
        	padding: 10px 20px;
        	width: 100%;
        	box-sizing: border-box;
        }
      </style>

      <vaadin-vertical-layout id="layout"></vaadin-vertical-layout>    
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
