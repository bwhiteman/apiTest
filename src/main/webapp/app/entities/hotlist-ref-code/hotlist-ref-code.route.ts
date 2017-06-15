import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { HotlistRefCodeComponent } from './hotlist-ref-code.component';
import { HotlistRefCodeDetailComponent } from './hotlist-ref-code-detail.component';
import { HotlistRefCodePopupComponent } from './hotlist-ref-code-dialog.component';
import { HotlistRefCodeDeletePopupComponent } from './hotlist-ref-code-delete-dialog.component';

import { Principal } from '../../shared';

export const hotlistRefCodeRoute: Routes = [
    {
        path: 'hotlist-ref-code',
        component: HotlistRefCodeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apiTestApp.hotlistRefCode.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'hotlist-ref-code/:id',
        component: HotlistRefCodeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apiTestApp.hotlistRefCode.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const hotlistRefCodePopupRoute: Routes = [
    {
        path: 'hotlist-ref-code-new',
        component: HotlistRefCodePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apiTestApp.hotlistRefCode.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'hotlist-ref-code/:id/edit',
        component: HotlistRefCodePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apiTestApp.hotlistRefCode.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'hotlist-ref-code/:id/delete',
        component: HotlistRefCodeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apiTestApp.hotlistRefCode.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
