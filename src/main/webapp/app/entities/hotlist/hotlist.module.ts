import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ApiTestSharedModule } from '../../shared';
import {
    HotlistService,
    HotlistPopupService,
    HotlistComponent,
    HotlistDetailComponent,
    HotlistDialogComponent,
    HotlistPopupComponent,
    HotlistDeletePopupComponent,
    HotlistDeleteDialogComponent,
    hotlistRoute,
    hotlistPopupRoute,
} from './';

const ENTITY_STATES = [
    ...hotlistRoute,
    ...hotlistPopupRoute,
];

@NgModule({
    imports: [
        ApiTestSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        HotlistComponent,
        HotlistDetailComponent,
        HotlistDialogComponent,
        HotlistDeleteDialogComponent,
        HotlistPopupComponent,
        HotlistDeletePopupComponent,
    ],
    entryComponents: [
        HotlistComponent,
        HotlistDialogComponent,
        HotlistPopupComponent,
        HotlistDeleteDialogComponent,
        HotlistDeletePopupComponent,
    ],
    providers: [
        HotlistService,
        HotlistPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ApiTestHotlistModule {}
