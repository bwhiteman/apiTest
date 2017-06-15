import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ApiTestSharedModule } from '../../shared';
import {
    HotlistRefCodeService,
    HotlistRefCodePopupService,
    HotlistRefCodeComponent,
    HotlistRefCodeDetailComponent,
    HotlistRefCodeDialogComponent,
    HotlistRefCodePopupComponent,
    HotlistRefCodeDeletePopupComponent,
    HotlistRefCodeDeleteDialogComponent,
    hotlistRefCodeRoute,
    hotlistRefCodePopupRoute,
} from './';

const ENTITY_STATES = [
    ...hotlistRefCodeRoute,
    ...hotlistRefCodePopupRoute,
];

@NgModule({
    imports: [
        ApiTestSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        HotlistRefCodeComponent,
        HotlistRefCodeDetailComponent,
        HotlistRefCodeDialogComponent,
        HotlistRefCodeDeleteDialogComponent,
        HotlistRefCodePopupComponent,
        HotlistRefCodeDeletePopupComponent,
    ],
    entryComponents: [
        HotlistRefCodeComponent,
        HotlistRefCodeDialogComponent,
        HotlistRefCodePopupComponent,
        HotlistRefCodeDeleteDialogComponent,
        HotlistRefCodeDeletePopupComponent,
    ],
    providers: [
        HotlistRefCodeService,
        HotlistRefCodePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ApiTestHotlistRefCodeModule {}
