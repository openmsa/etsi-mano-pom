/**
 *     Copyright (C) 2019-2024 Ubiqube.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see https://www.gnu.org/licenses/.
 */
package com.ubiqube.etsi.mano.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.ubiqube.etsi.mano.utils.ResourceUtils.buildResourcePath;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ResourceUtilsTest {

    @Test
    public void buildResourcePath_nominalCase() {
        assertEquals("/abc", buildResourcePath("abc"));

        assertEquals("/abc//d", buildResourcePath("abc", "/d"));

        assertEquals("//abc//", buildResourcePath("/abc", "/"));

        assertEquals("", buildResourcePath(null));
        assertEquals("/abc", buildResourcePath("abc", null));
    }
}
