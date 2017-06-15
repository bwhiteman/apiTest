import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { HotlistComponent } from './hotlist.component';
import { HotlistDetailComponent } from './hotlist-detail.component';
import { HotlistPopupComponent } from './hotlist-dialog.component';
import { HotlistDeletePopupComponent } from './hotlist-delete-dialog.component';

import { Principal } from '../../shared';

export const hotlistRoute: Routes = [
    {
        path: 'hotlist',
        component: HotlistComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apiTestApp.hotlist.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'hotlist/:id',
        component: HotlistDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apiTestApp.hotlist.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const hotlistPopupRoute: Routes = [
    {
        path: 'hotlist-new',
        component: HotlistPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apiTestApp.hotlist.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'hotlist/:id/edit',
        component: HotlistPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apiTestApp.hotlist.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'hotlist/:id/delete',
        component: HotlistDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apiTestApp.hotlist.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
