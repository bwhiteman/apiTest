import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ApiTestHotlistModule } from './hotlist/hotlist.module';
import { ApiTestHotlistRefCodeModule } from './hotlist-ref-code/hotlist-ref-code.module';
import { ApiTestPersonModule } from './person/person.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        ApiTestHotlistModule,
        ApiTestHotlistRefCodeModule,
        ApiTestPersonModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ApiTestEntityModule {}
