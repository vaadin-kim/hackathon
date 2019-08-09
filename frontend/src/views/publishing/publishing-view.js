import { PolymerElement } from "@polymer/polymer/polymer-element.js";
import { html } from "@polymer/polymer/lib/utils/html-tag.js";
import "@vaadin/vaadin-split-layout/vaadin-split-layout.js";
import "@vaadin/vaadin-grid/vaadin-grid.js";
import "@vaadin/vaadin-grid/vaadin-grid-column.js";
import "@vaadin/vaadin-form-layout/vaadin-form-layout.js";
import "@vaadin/vaadin-form-layout/vaadin-form-item.js";
import "@vaadin/vaadin-text-field/vaadin-text-field.js";
import "@vaadin/vaadin-text-field/vaadin-password-field.js";
import "@vaadin/vaadin-button/vaadin-button.js";
import "@vaadin/vaadin-ordered-layout/vaadin-horizontal-layout.js";



class PublishingView extends PolymerElement {
  static get template() {
    return html`
      <style include="shared-styles">
        :host {
          display: block;
          height: 100%;
        }
      </style>
       <script src="https://cdn.tiny.cloud/1/z0nwvw4gz3dequvxnf86feh7i0xss1mkbveu68ujfjrid8ki/tinymce/5/tinymce.min.js"></script>
  <script>
  
  </script>

      <vaadin-split-layout style="width: 100%; height: 100%;">
        <div style="flex-grow:2;width:100%;" id="wrapper">
          <vaadin-grid style="height:100%" theme="no-border" id="news">
          </vaadin-grid>
        </div>
        <div style="flex-grow:1;width:100%;"adding:var(--lumo-space-l)">
          <vaadin-text-area style="height: 500px; width:100%" id="article"></vaadin-text-area>
          <vaadin-horizontal-layout
            style="display:flex;flex-wrap:wrap-reverse;width:100%;justify-content:space-between;"
          >
          	<vaadin-button theme="secondary" slot="" id="create-new" style="">
              Create new
            </vaadin-button>
            <vaadin-horizontal-layout theme="spacing">
            	<vaadin-button theme="tertiary" slot="" id="cancel">
              		Cancel
            	</vaadin-button>
            	<vaadin-button theme="primary" id="save">
              		Save
            	</vaadin-button>
            </vaadin-horizontal-layout>
          </vaadin-horizontal-layout>
        </div>
      </vaadin-split-layout>
    `;
  }


  static get is() {
    return "publishing-view";
  }

  static get properties() {
    return {
      // Declare your properties here.
    };
  } 
}

customElements.define(PublishingView.is, PublishingView);
